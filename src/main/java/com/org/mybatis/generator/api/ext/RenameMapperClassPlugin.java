package com.org.mybatis.generator.api.ext;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

public class RenameMapperClassPlugin extends PluginAdapter {

  private String replaceSuffixStr;
  private Pattern pattern;

  /**
   * This method is called after all the setXXX methods are called, but before any other method is
   * called. This allows the plugin to determine whether it can run or not. For example, if the
   * plugin requires certain properties to be set, and the properties are not set, then the plugin
   * is invalid and will not run.
   *
   * @param warnings add strings to this list to specify warnings. For example, if the plugin is
   * invalid, you should specify why. Warnings are reported to users after the completion of the
   * run.
   * @return true if the plugin is in a valid state. Invalid plugins will not be called
   */
  @Override
  public boolean validate(List<String> warnings) {
    String oldSuffixStr = properties.getProperty("oldSuffixStr"); //$NON-NLS-1$
    replaceSuffixStr = properties.getProperty("replaceSuffixStr"); //$NON-NLS-1$

    boolean valid = stringHasValue(oldSuffixStr)
        && stringHasValue(replaceSuffixStr);

    if (valid) {
      pattern = Pattern.compile(oldSuffixStr);
    } else {
      if (!stringHasValue(oldSuffixStr)) {
        warnings.add(getString("ValidationError.18", //$NON-NLS-1$
            "RenameMapperClassPlugin", //$NON-NLS-1$
            "oldSuffixStr")); //$NON-NLS-1$
      }
      if (!stringHasValue(replaceSuffixStr)) {
        warnings.add(getString("ValidationError.18", //$NON-NLS-1$
            "RenameMapperClassPlugin", //$NON-NLS-1$
            "replaceSuffixStr")); //$NON-NLS-1$
      }
    }

    return valid;
  }

  @Override
  public void initialized(IntrospectedTable introspectedTable) {
    String oldType = introspectedTable.getMyBatis3JavaMapperType();
    Matcher matcher = pattern.matcher(oldType);
    oldType = matcher.replaceAll(replaceSuffixStr);

    introspectedTable.setMyBatis3JavaMapperType(oldType);
  }
}
