package com.org.mybatis.generator.api.ext;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;

public class JavaClientPlugin extends PluginAdapter {

  private String javaClientReplaceSuffixStr;
  private Pattern javaClientPattern;

  private String sqlMapReplaceSuffixStr;
  private Pattern sqlMapPattern;

  /**
   * This method valid params for renaming JavaClient suffix filename and SqlMap suffix filename.
   */
  @Override
  public boolean validate(List<String> warnings) {
    String javaClientOldSuffixStr = properties.getProperty("javaClientOldSuffixStr");
    javaClientReplaceSuffixStr = properties.getProperty("javaClientReplaceSuffixStr");

    String sqlMapOldSuffixStr = properties.getProperty("sqlMapOldSuffixStr");
    sqlMapReplaceSuffixStr = properties.getProperty("sqlMapReplaceSuffixStr");

    // java client params valid.
    boolean valid_1 = stringHasValue(javaClientOldSuffixStr)
        && stringHasValue(javaClientReplaceSuffixStr);

    if (valid_1) {
      javaClientPattern = Pattern.compile(javaClientOldSuffixStr);
    } else {
      if (!stringHasValue(javaClientOldSuffixStr)) {
        warnings.add(getString("ValidationError.18",
            "com.org.mybatis.generator.api.ext.JavaClientPlugin",
            "javaClientOldSuffixStr"));
      }
      if (!stringHasValue(javaClientReplaceSuffixStr)) {
        warnings.add(getString("ValidationError.18",
            "com.org.mybatis.generator.api.ext.JavaClientPlugin",
            "javaClientReplaceSuffixStr"));
      }
    }

    // sql map params valid.
    boolean valid_2 = stringHasValue(sqlMapOldSuffixStr)
        && stringHasValue(sqlMapReplaceSuffixStr);

    if (valid_2) {
      sqlMapPattern = Pattern.compile(sqlMapOldSuffixStr);
    } else {
      if (!stringHasValue(sqlMapOldSuffixStr)) {
        warnings.add(getString("ValidationError.18",
            "com.org.mybatis.generator.api.ext.JavaClientPlugin",
            "sqlMapOldSuffixStr"));
      }
      if (!stringHasValue(sqlMapReplaceSuffixStr)) {
        warnings.add(getString("ValidationError.18",
            "com.org.mybatis.generator.api.ext.JavaClientPlugin",
            "sqlMapReplaceSuffixStr"));
      }
    }

    return valid_1 || valid_2;
  }

  /**
   * Rename JavaClient suffix filename and SqlMap suffix filename.
   */
  @Override
  public void initialized(IntrospectedTable introspectedTable) {
    String type_1 = introspectedTable.getMyBatis3JavaMapperType();
    String type_2 = introspectedTable.getMyBatis3XmlMapperFileName();

    Matcher matcher_1 = javaClientPattern.matcher(type_1);
    Matcher matcher_2 = sqlMapPattern.matcher(type_2);

    type_1 = matcher_1.replaceAll(javaClientReplaceSuffixStr);
    type_2 = matcher_2.replaceAll(sqlMapReplaceSuffixStr);

    introspectedTable.setMyBatis3JavaMapperType(type_1);
    introspectedTable.setMyBatis3XmlMapperFileName(type_2);
  }

  /**
   * Add imports and annotations for JavaClient class.
   */
  @Override
  public boolean clientGenerated(Interface interfaze,
      IntrospectedTable introspectedTable) {
    // add import class
    interfaze
        .addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"));
    // add annotation
    interfaze.addAnnotation("@Mapper");
    return true;
  }
}
