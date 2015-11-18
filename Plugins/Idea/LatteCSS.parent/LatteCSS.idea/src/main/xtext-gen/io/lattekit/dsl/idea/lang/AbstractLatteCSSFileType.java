/*
 * generated by Xtext 2.9.0.beta5
 */
package io.lattekit.dsl.idea.lang;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import javax.swing.Icon;
import org.eclipse.xtext.idea.Icons;
import org.jetbrains.annotations.NonNls;

public class AbstractLatteCSSFileType extends LanguageFileType {

	@NonNls 
	public static final String DEFAULT_EXTENSION = "css";

	protected AbstractLatteCSSFileType(final Language language) {
		super(language);
	}

	@Override
	public String getDefaultExtension() {
		return DEFAULT_EXTENSION;
	}

	@Override
	public String getDescription() {
		return "LatteCSS files";
	}

	@Override
	public Icon getIcon() {
		return Icons.DSL_FILE_TYPE;
	}

	@Override
	public String getName() {
		return "LatteCSS";
	}

}