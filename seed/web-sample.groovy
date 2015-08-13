def project = "web-sample"

freeStyleJob("${project}-build") {

    description("A job to compile, unit-test, package and make distributed tarball")

    scm {
        git {
            remote {
                url("https://github.com/mylesjao/jenkins-job-dsl-sample.git")
            }
            branch("job-dsl")
            createTag(false)
        }
    }

    triggers {
        scm('H/15 * * * *')
    }

    steps {
        shell("build/build.sh")
    }

    publishers {
        downstream("${project}-dist", "SUCCESS")
    }

}

freeStyleJob("${project}-dist") {

    description("A job to make a docker distribution")

    steps {
        shell("echo 'dist....'")
    }

    publishers {
        downstream("${project}-publish", "SUCCESS")
    }
}

freeStyleJob("${project}-publish") {

    description("A job to publish docker image to registry")

    steps {
        shell("echo 'publish....'")
    }

    publishers {
        downstream("${project}-deploy", "SUCCESS")
    }

}

freeStyleJob("${project}-deploy") {

    description("A job to pull docker from registry and run")

    steps {
        shell("echo 'deploy...'")
    }

}

listView("${project}") {
    description("All jobs for ${project}")
    filterBuildQueue(true)
    filterExecutors(true)

    jobs {
        name("${project}")
        regex("${project}-.*")
    }

    columns {
        status()
        weather()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
    }
}