/**
 */
package io.lattekit.dsl.latteCSS;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Shorthand Size Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.lattekit.dsl.latteCSS.ShorthandSizeProperty#getValues <em>Values</em>}</li>
 * </ul>
 *
 * @see io.lattekit.dsl.latteCSS.LatteCSSPackage#getShorthandSizeProperty()
 * @model
 * @generated
 */
public interface ShorthandSizeProperty extends CSSProperty
{
  /**
   * Returns the value of the '<em><b>Values</b></em>' containment reference list.
   * The list contents are of type {@link io.lattekit.dsl.latteCSS.SizeValue}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Values</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Values</em>' containment reference list.
   * @see io.lattekit.dsl.latteCSS.LatteCSSPackage#getShorthandSizeProperty_Values()
   * @model containment="true"
   * @generated
   */
  EList<SizeValue> getValues();

} // ShorthandSizeProperty