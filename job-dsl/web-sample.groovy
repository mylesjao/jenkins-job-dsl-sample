def project = "web-sample"

freeStyleJob("${project}-build") {

    description("A job to compile, unit-test, package and make distributed tarball")

    scm {
        git {
            remote {
                url("https://github.com/mylesjao/jenkins-job-dsl-sample.git")
            }
            branch("master")
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

        publishCloneWorkspace("dist/**/*,build/**/*,version.sbt") {
            criteria("Successful") // 'Not Failed', 'Any', 'Successful'
            archiveMethod("TAR") // 'ZIP', 'TAR'
        }

        downstream("${project}-dist", "SUCCESS")
    }

    logRotator {
        daysToKeep(1)
        numToKeep(1)
    }

}

freeStyleJob("${project}-dist") {

    description("A job to make a docker distribution")

    scm {
        cloneWorkspace("${project}-build", "Successful")
    }

    steps {
        shell("build/dist.sh")
    }

    publishers {

        publishCloneWorkspace("build/**/*,version.sbt") {
            criteria("Successful") // 'Not Failed', 'Any', 'Successful'
            archiveMethod("TAR") // 'ZIP', 'TAR'
        }

        downstream("${project}-publish", "SUCCESS")
    }

    logRotator {
        daysToKeep(1)
        numToKeep(1)
    }
}

freeStyleJob("${project}-publish") {

    description("A job to publish docker image to registry")

    scm {
        cloneWorkspace("${project}-dist", "Successful")
    }

    steps {
        shell("build/publish.sh")
    }

    publishers {

        publishCloneWorkspace("build/**/*,version.sbt") {
            criteria("Successful") // 'Not Failed', 'Any', 'Successful'
            archiveMethod("TAR") // 'ZIP', 'TAR'
        }

        downstream("${project}-deploy", "SUCCESS")
    }

    logRotator {
        daysToKeep(1)
        numToKeep(1)
    }

}

freeStyleJob("${project}-deploy") {

    description("A job to pull docker image from registry and run")

    scm {
        cloneWorkspace("${project}-publish", "Successful")
    }

    steps {
        shell("build/deploy.sh")
    }

    logRotator {
        daysToKeep(1)
        numToKeep(1)
    }

}

listView("${project}-list") {
    description("List all jobs of ${project}")
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

nestedView("${project}-nested") {
    description("All job of ${project} in nested view")
    filterBuildQueue(true)
    filterExecutors(true)

    columns {
        status()
        weather()
    }

    views {
        listView("${project}-list") {
            description("List all jobs of ${project}")
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

        buildPipelineView("${project}-pipeline") {
            description("job pipeline of ${project}")
            title("${project}")
            filterBuildQueue(true)
            filterExecutors(true)
            selectedJob("${project}-build")
        }
    }

}