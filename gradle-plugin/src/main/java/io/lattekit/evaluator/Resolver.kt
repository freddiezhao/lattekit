package io.lattekit.evaluator

import io.lattekit.parser.*
import io.lattekit.transformer.Reflection

/**
 * Created by maan on 4/2/16.
 */
class Resolver(var androidPackage: String) {
    var latteFile : LatteFile? = null;

    fun evaluate(file : LatteFile) {
        file.androidPackageName = androidPackage;
        latteFile = file;
        file.classes.forEach {
            it.classNameImpl = it.className+"Impl"
            it.layoutFunctions.forEach { evaluateLayoutFunction(it); }
        }
    }
    fun evaluateLayoutFunction(layoutFunction: LayoutFunction) {
        layoutFunction.children.forEach {
            evaluateLayoutNode(it)
        }
    }

    fun evaluateXmlTag(tag : XmlTag) {
        tag.viewClass = Reflection.lookupClass(tag.tagName)
        tag.props.forEach { prop ->
            if (prop.valueType == Prop.ValueType.RESOURCE_REF) {
                prop.resourcePackage = prop.resourcePackage ?: latteFile?.androidPackageName
            }
        }
        if (tag.viewClass != null) {
            tag.isNativeView = true;
            tag.props.forEach {
                evaluateProperty(it, tag)
            }
        }
        tag.children.forEach { evaluateLayoutNode(it) }
    }



    fun evaluateProperty(prop : Prop, tag: XmlTag) {
        var propName = prop.propName;
        var field = if (propName.startsWith("@")) {
            propName.substring(1)
        } else propName;

        var setter = "set" + field.substring(0, 1).toUpperCase() + field.substring(1)
        var getter =  "get" + field.substring(0, 1).toUpperCase() + field.substring(1)
        var getterBoolean =  "is"+ field.substring(0, 1).toUpperCase() + field.substring(1);
        var setterMethods = Reflection.findMethods(tag.viewClass, setter);

        if (setterMethods.isEmpty() && field != "onClick" && field != "onTouch") {
            setter += "Listener";
            getter += "Listener";
            setterMethods = Reflection.findMethods(tag.viewClass, setter);
            prop.isListenerProp = setterMethods.isNotEmpty()
        }

        val getterMethods = Reflection.findGetterMethods(tag.viewClass,getter) + Reflection.findGetterMethods(tag.viewClass, getterBoolean);
        setterMethods.forEach { setterMethod ->
            var propSetter = PropSetter();
            propSetter.setterMethod = setterMethod;
            propSetter.paramType = setterMethod.parameters[0].type
            propSetter.isPrimitiveType = setterMethod.parameters[0].type.isPrimitive;
            if (setterMethod.parameters[0].type == String::class.java || setterMethod.parameters[0].type == CharSequence::class.java) {
                prop.isHasStringSetter = true;
            }
            var getterMethod = getterMethods.find { it.name == getter && ( (it.returnType.isAssignableFrom(propSetter.paramType) || propSetter.paramType.isAssignableFrom(it.returnType))) }
            if (getterMethod != null) {
                propSetter.isHasGetter = true;
                propSetter.getterMethod = getterMethod;
            }
            prop.propSetters.add(propSetter);
        }
        if (setterMethods.isEmpty()) {
            // TODO: Handle this properly when BindingMethods/Blueprints is implemented
            if (!(propName.startsWith("layout_") || propName == "class" || (propName == "src" && tag.tagName == "ImageView"))
               || (propName == "data" && (tag.tagName == "android.support.v7.widget.RecyclerView" || tag.tagName == "ListView")) || propName.startsWith("on")) {
                latteFile?.warnings?.add("Warning: couldn't recognize property $propName for ${tag.tagName}");
            }
        }

    }
    fun evaluateLayoutNode(node : LayoutNode) {
        if (node is XmlTag) {
            evaluateXmlTag(node)
        }
    }
}
