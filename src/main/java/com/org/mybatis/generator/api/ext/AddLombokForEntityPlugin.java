package com.org.mybatis.generator.api.ext;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.List;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class AddLombokForEntityPlugin extends PluginAdapter {

  private final String MY_SUPPER_CLASS = "supperClass";
  private String supperClass;

  public AddLombokForEntityPlugin() {
  }

  @Override
  public boolean validate(List<String> warnings) {
    boolean valid = true;
    supperClass = properties.getProperty(MY_SUPPER_CLASS);
    if (supperClass != null && !"".equals(supperClass)) {
      try {
        Class.forName(supperClass);
      } catch (ClassNotFoundException e) {
        warnings.add(getString("ValidationError.18",
            "com.org.mybatis.generator.api.comment.AddLombokForEntityPlugin",
            "MY_SUPPER_CLASS"));
        valid = false;
      }
    }
    return valid;
  }

  @Override
  public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
      IntrospectedTable introspectedTable) {
    // import
    topLevelClass.addImportedType("lombok.Data");
    topLevelClass.addImportedType("lombok.NoArgsConstructor");
    topLevelClass.addImportedType("lombok.AllArgsConstructor");
    topLevelClass.addImportedType("lombok.EqualsAndHashCode");

    topLevelClass.addAnnotation("@Data");
    topLevelClass.addAnnotation("@NoArgsConstructor");
    topLevelClass.addAnnotation("@AllArgsConstructor");
    topLevelClass.addAnnotation("@EqualsAndHashCode(callSuper = false)");

    if (supperClass != null && !"".equals(supperClass)) {
      topLevelClass.setSuperClass(new FullyQualifiedJavaType(supperClass));
    }
    String generatorDefaultSerialVersionUID = properties
        .getProperty("defaultSerialVersionUID");
    if ("true".equals(generatorDefaultSerialVersionUID)) {
      generatorDefaultSerialVersionUID(topLevelClass);
    }

    return true;
  }

  @Override
  public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass,
      IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable,
      ModelClassType modelClassType) {
    return false;
  }

  @Override
  public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass,
      IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable,
      ModelClassType modelClassType) {
    return false;
  }

  private void generatorDefaultSerialVersionUID(TopLevelClass topLevelClass) {
    Field field = new Field("serialVersionUID", new FullyQualifiedJavaType("long"));
    field.setFinal(true);
    field.setInitializationString("1L");
    field.setStatic(true);
    field.setVisibility(JavaVisibility.PRIVATE);
    topLevelClass.getFields().add(0, field);
  }
}
