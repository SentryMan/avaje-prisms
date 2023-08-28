package io.avaje.prism.internal;

import java.io.PrintWriter;

public class APContextWriter {
  private APContextWriter() {}

  public static void write(PrintWriter out, String packageName) {

    out.append(
        "package "
            + packageName
            + ";\n"
            + "\n"
            + "import static java.util.function.Predicate.not;\n"
            + "\n"
            + "import java.io.BufferedReader;\n"
            + "import java.io.IOException;\n"
            + "import java.io.InputStreamReader;\n"
            + "import java.util.Collection;\n"
            + "import java.util.Optional;\n"
            + "import java.util.Set;\n"
            + "import java.util.stream.Stream;\n"
            + "\n"
            + "import javax.annotation.processing.Filer;\n"
            + "import javax.annotation.processing.Generated;\n"
            + "import javax.annotation.processing.Messager;\n"
            + "import javax.annotation.processing.ProcessingEnvironment;\n"
            + "import javax.annotation.processing.RoundEnvironment;\n"
            + "import javax.lang.model.element.Element;\n"
            + "import javax.lang.model.element.ModuleElement;\n"
            + "import javax.lang.model.element.TypeElement;\n"
            + "import javax.lang.model.type.TypeMirror;\n"
            + "import javax.lang.model.util.Elements;\n"
            + "import javax.lang.model.util.Types;\n"
            + "import javax.tools.Diagnostic;\n"
            + "import javax.tools.JavaFileObject;\n"
            + "import javax.tools.StandardLocation;\n"
            + "\n"
            + "/**\n"
            + " * Utiliy Class that stores the {@link ProcessingEnvironment} and provides various helper methods\n"
            + " */\n"
            + "@Generated(\"avaje-prism-generator\")\n"
            + "public final class APContext {\n"
            + "\n"
            + "  private static int jdkVersion;\n"
            + "  private static boolean previewEnabled;\n"
            + "  private static final ThreadLocal<Ctx> CTX = new ThreadLocal<>();\n"
            + "\n"
            + "  private APContext() {}\n"
            + "\n"
            + "  public static final class Ctx {\n"
            + "    private final ProcessingEnvironment processingEnv;\n"
            + "    private final Messager messager;\n"
            + "    private final Filer filer;\n"
            + "    private final Elements elementUtils;\n"
            + "    private final Types typeUtils;\n"
            + "    private ModuleElement module;\n"
            + "\n"
            + "    public Ctx(ProcessingEnvironment processingEnv) {\n"
            + "\n"
            + "      this.processingEnv = processingEnv;\n"
            + "      messager = processingEnv.getMessager();\n"
            + "      filer = processingEnv.getFiler();\n"
            + "      elementUtils = processingEnv.getElementUtils();\n"
            + "      typeUtils = processingEnv.getTypeUtils();\n"
            + "    }\n"
            + "\n"
            + "    public Ctx(Messager messager, Filer filer, Elements elementUtils, Types typeUtils) {\n"
            + "\n"
            + "      this.processingEnv = null;\n"
            + "      this.messager = messager;\n"
            + "      this.filer = filer;\n"
            + "      this.elementUtils = elementUtils;\n"
            + "      this.typeUtils = typeUtils;\n"
            + "    }\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Initialize the ThreadLocal containing the Processing Enviroment. this typically should be\n"
            + "   * called during the init phase of processing. Be sure to run the clear method at the last round\n"
            + "   * of processing\n"
            + "   *\n"
            + "   * @param processingEnv the current annotation processing enviroment\n"
            + "   */\n"
            + "  public static void init(ProcessingEnvironment processingEnv) {\n"
            + "    CTX.set(new Ctx(processingEnv));\n"
            + "    jdkVersion = processingEnv.getSourceVersion().ordinal();\n"
            + "    previewEnabled = jdkVersion >= 13 && initPreviewEnabled(processingEnv);\n"
            + "  }\n"
            + "\n"
            + "  private static boolean initPreviewEnabled(ProcessingEnvironment processingEnv) {\n"
            + "    try {\n"
            + "      return (boolean)\n"
            + "          ProcessingEnvironment.class.getDeclaredMethod(\"isPreviewEnabled\").invoke(processingEnv);\n"
            + "    } catch (final Throwable e) {\n"
            + "      return false;\n"
            + "    }\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Initialize the ThreadLocal containing the {@link ProcessingEnvironment}. Be sure to run the\n"
            + "   * clear method at the last round of processing\n"
            + "   *\n"
            + "   * @param processingEnv the current annotation processing enviroment\n"
            + "   */\n"
            + "  public static void init(Ctx context) {\n"
            + "    CTX.set(context);\n"
            + "  }\n"
            + "\n"
            + "  /** Clears the ThreadLocal containing the {@link ProcessingEnvironment}. */\n"
            + "  public static void clear() {\n"
            + "    CTX.remove();\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Returns the source version that any generated source and class files should conform to\n"
            + "   *\n"
            + "   * @return the source version as an int\n"
            + "   */\n"
            + "  public static int jdkVersion() {\n"
            + "    return jdkVersion;\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Returns whether {@code --preview-enabled} has been added to compiler flags.\n"
            + "   *\n"
            + "   * @return true if preview features are enabled\n"
            + "   */\n"
            + "  public static boolean previewEnabled() {\n"
            + "    return previewEnabled;\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Prints an error at the location of the element.\n"
            + "   *\n"
            + "   * @param e the element to use as a position hint\n"
            + "   * @param msg the message, or an empty string if none\n"
            + "   * @param args {@code String#format} arguments\n"
            + "   */\n"
            + "  public static void logError(Element e, String msg, Object... args) {\n"
            + "    messager().printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Prints an error.\n"
            + "   *\n"
            + "   * @param msg the message, or an empty string if none\n"
            + "   * @param args {@code String#format} arguments\n"
            + "   */\n"
            + "  public static void logError(String msg, Object... args) {\n"
            + "    messager().printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Prints an warning at the location of the element.\n"
            + "   *\n"
            + "   * @param e the element to use as a position hint\n"
            + "   * @param msg the message, or an empty string if none\n"
            + "   * @param args {@code String#format} arguments\n"
            + "   */\n"
            + "  public static void logWarn(Element e, String msg, Object... args) {\n"
            + "    messager().printMessage(Diagnostic.Kind.WARNING, String.format(msg, args), e);\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Prints a warning.\n"
            + "   *\n"
            + "   * @param msg the message, or an empty string if none\n"
            + "   * @param args {@code String#format} arguments\n"
            + "   */\n"
            + "  public static void logWarn(String msg, Object... args) {\n"
            + "    messager().printMessage(Diagnostic.Kind.WARNING, String.format(msg, args));\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Prints a note.\n"
            + "   *\n"
            + "   * @param msg the message, or an empty string if none\n"
            + "   * @param args {@code String#format} arguments\n"
            + "   */\n"
            + "  public static void logNote(Element e, String msg, Object... args) {\n"
            + "    messager().printMessage(Diagnostic.Kind.NOTE, String.format(msg, args), e);\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Prints a note at the location of the element.\n"
            + "   *\n"
            + "   * @param e the element to use as a position hint\n"
            + "   * @param msg the message, or an empty string if none\n"
            + "   * @param args {@code String#format} arguments\n"
            + "   */\n"
            + "  public static void logNote(String msg, Object... args) {\n"
            + "    messager().printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Create a file writer for the given class name.\n"
            + "   *\n"
            + "   * @param name canonical (fully qualified) name of the principal class or interface being declared\n"
            + "   *     in this file or a package name followed by {@code \".package-info\"} for a package\n"
            + "   *     information file\n"
            + "   * @param originatingElements class, interface, package, or module elements causally associated\n"
            + "   *     with the creation of this file, may be elided or {@code null}\n"
            + "   * @return a JavaFileObject to write the new source file\n"
            + "   */\n"
            + "  public static JavaFileObject createSourceFile(CharSequence name, Element... originatingElements)\n"
            + "      throws IOException {\n"
            + "    return filer().createSourceFile(name, originatingElements);\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Returns a type element given its canonical name.\n"
            + "   *\n"
            + "   * @param name the canonical name\n"
            + "   * @return the named type element, or null if no type element can be uniquely determined\n"
            + "   */\n"
            + "  public static TypeElement typeElement(String name) {\n"
            + "    return elements().getTypeElement(name);\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Returns the element corresponding to a type.The type may be a DeclaredType or\n"
            + "   * TypeVariable.Returns null if the type is not one with a corresponding element.\n"
            + "   *\n"
            + "   * @param t the type to map to an element\n"
            + "   * @return the element corresponding to the given type\n"
            + "   */\n"
            + "  public static Element asElement(TypeMirror t) {\n"
            + "\n"
            + "    return types().asElement(t);\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Get current {@link ProcessingEnvironment}\n"
            + "   *\n"
            + "   * @return the enviroment\n"
            + "   */\n"
            + "  public static ProcessingEnvironment processingEnv() {\n"
            + "    return CTX.get().processingEnv;\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Get current {@link Filer} from the {@link ProcessingEnvironment}\n"
            + "   *\n"
            + "   * @return the filer\n"
            + "   */\n"
            + "  public static Filer filer() {\n"
            + "    return CTX.get().filer;\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Get current {@link Elements} from the {@link ProcessingEnvironment}\n"
            + "   *\n"
            + "   * @return the filer\n"
            + "   */\n"
            + "  public static Elements elements() {\n"
            + "    return CTX.get().elementUtils;\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Get current {@link Messager} from the {@link ProcessingEnvironment}\n"
            + "   *\n"
            + "   * @return the messager\n"
            + "   */\n"
            + "  public static Messager messager() {\n"
            + "    return CTX.get().messager;\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Get current {@link Types} from the {@link ProcessingEnvironment}\n"
            + "   *\n"
            + "   * @return the types\n"
            + "   */\n"
            + "  public static Types types() {\n"
            + "    return CTX.get().typeUtils;\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Determine whether the first type can be assigned to the second\n"
            + "   *\n"
            + "   * @param type string type to check\n"
            + "   * @param superType the type that should be assignable to.\n"
            + "   * @return true if type can be assinged to supertype\n"
            + "   */\n"
            + "  public static boolean isAssignable(String type, String superType) {\n"
            + "    return type.equals(superType) || isAssignable(typeElement(type), superType);\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Determine whether the first type can be assigned to the second\n"
            + "   *\n"
            + "   * @param type type to check\n"
            + "   * @param superType the type that should be assignable to.\n"
            + "   * @return true if type can be assinged to supertype\n"
            + "   */\n"
            + "  public static boolean isAssignable(TypeElement type, String superType) {\n"
            + "    return Optional.ofNullable(type).stream()\n"
            + "        .flatMap(APContext::superTypes)\n"
            + "        .anyMatch(superType::equals);\n"
            + "  }\n"
            + "\n"
            + "  private static Stream<String> superTypes(TypeElement element) {\n"
            + "    final var types = types();\n"
            + "    return types.directSupertypes(element.asType()).stream()\n"
            + "        .filter(type -> !type.toString().contains(\"java.lang.Object\"))\n"
            + "        .map(superType -> (TypeElement) types.asElement(superType))\n"
            + "        .flatMap(e -> Stream.concat(superTypes(e), Stream.of(e)))\n"
            + "        .map(Object::toString);\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Discover the {@link ModuleElement} for the project being processed and set in the context.\n"
            + "   *\n"
            + "   * @param annotations the annotation interfaces requested to be processed\n"
            + "   * @param roundEnv environment for information about the current and prior round\n"
            + "   */\n"
            + "  public static void setProjectModuleElement(\n"
            + "      Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {\n"
            + "    if (CTX.get().module == null) {\n"
            + "      CTX.get().module =\n"
            + "          annotations.stream()\n"
            + "              .map(roundEnv::getElementsAnnotatedWith)\n"
            + "              .filter(not(Collection::isEmpty))\n"
            + "              .findAny()\n"
            + "              .map(s -> s.iterator().next())\n"
            + "              .map(elements()::getModuleOf)\n"
            + "              .orElse(null);\n"
            + "    }\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Retrieve the project's {@link ModuleElement}. {@code setProjectModuleElement} must be called\n"
            + "   * before this.\n"
            + "   *\n"
            + "   * @return the {@link ModuleElement} associated with the current project\n"
            + "   */\n"
            + "  public static ModuleElement getProjectModuleElement() {\n"
            + "    return CTX.get().module;\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * Gets a {@link BufferedReader} for the project's {@code module-info.java} source file.\n"
            + "   *\n"
            + "   * <p>Calling {@link ModuleElement}'s {@code getDirectives()} method has a chance of making\n"
            + "   * compilation fail in certain situations. Therefore, manually parsing {@code module-info.java}\n"
            + "   * seems to be the safest way to get module information.\n"
            + "   *\n"
            + "   * @return\n"
            + "   * @throws IOException if unable to read the module-info\n"
            + "   */\n"
            + "  public static BufferedReader getModuleInfoReader() throws IOException {\n"
            + "    var inputStream =\n"
            + "        filer()\n"
            + "            .getResource(StandardLocation.SOURCE_PATH, \"\", \"module-info.java\")\n"
            + "            .toUri()\n"
            + "            .toURL()\n"
            + "            .openStream();\n"
            + "    return new BufferedReader(new InputStreamReader(inputStream));\n"
            + "  }\n"
            + "}\n"
            + "");
  }
}