import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class ProjectVersion {
	Integer major

	ProjectVersion(Integer ins) {
		major = ins
	}

	String toString() {
		"ProjectVersion 输出major = $major"
	}
}

class GGTask extends DefaultTask {

	@TaskAction
	void action() {
		println "自定义的Task"
	}
}