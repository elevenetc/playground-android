package su.levenetc.androidplayground;

import com.example.IntentField;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class IntentFieldsProcessor extends AbstractProcessor {

	@Override public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

	@Override public Set<String> getSupportedAnnotationTypes() {
		return Collections.singleton(IntentField.class.getCanonicalName());
	}

	@Override public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

		processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "GGHHHH");

		MethodSpec main = MethodSpec.methodBuilder("main")
				.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
				.returns(void.class)
				.addParameter(String[].class, "args")
				.addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
				.build();

		TypeSpec helloWorld = TypeSpec.classBuilder("GeneratedClass")
				.addModifiers(Modifier.PUBLIC, Modifier.FINAL)
				.addMethod(main)
				.build();

		JavaFile.builder("su.levenetc.androidplayground", helloWorld).build();

//		try {
//			javaFile.writeTo(System.out);
//		} catch (IOException e) {
//			processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.toString());
//			//e.printStackTrace();
//		}

		return true;
	}
}