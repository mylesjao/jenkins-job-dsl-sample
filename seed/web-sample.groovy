freeStyleJob("build") {
	
	description "A job to compile, unit-test, package and make distributed tarball"
	
	scm {
		git("https://github.com/mylesjao/jenkins-job-dsl-sample.git", "*/job-dsl") {
			createTag(false)
		}
	}

	triggers {
		scm('H/15 * * * *')
	}

	steps {
		shell("build/build.sh")
	}

}

freeStyleJob("dist") {

	description "A to make a docker image"
}

/*freeStyleJob("publish") {

	description "A job to publish docker image to registry"
	
}

freeStyleJob("deploy") {

	description "A job to pull docker from registry and run"
	
}*/