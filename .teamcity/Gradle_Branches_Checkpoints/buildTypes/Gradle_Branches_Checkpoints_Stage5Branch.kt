package Gradle_Branches_Checkpoints.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.GradleBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.GradleBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ScriptBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object Gradle_Branches_Checkpoints_Stage5Branch : BuildType({
    uuid = "0b4a6ade-eaf6-4718-849e-c3372180711e"
    extId = "Gradle_Branches_Checkpoints_Stage5Branch"
    name = "Stage 5 - Branch"
    description = "Passes all QA stages"

    artifactRules = "build/build-receipt.properties"

    vcs {
        root("Gradle_Branches_GradlePersonalBranches")
        checkoutMode = CheckoutMode.ON_AGENT
    }

    steps {
        gradle {
            name = "GRADLE_RUNNER"
            tasks = "createBuildReceipt"
            gradleParams = "-PtimestampedVersion"
            useGradleWrapper = true
        }
        script {
            name = "RUNNER_165"
            executionMode = BuildStep.ExecutionMode.ALWAYS
            scriptContent = """REPO=/home/%env.USER%/.m2/repository
if [ -e ${'$'}REPO ] ; then
echo "${'$'}REPO was polluted during the build"
return -1
else
echo "${'$'}REPO does not exist"
fi"""
        }
        script {
            name = "CHECK_CLEAN_M2"
            executionMode = BuildStep.ExecutionMode.ALWAYS
            scriptContent = """REPO=/home/%env.USER%/.m2/repository
if [ -e ${'$'}REPO ] ; then
rm -rf ${'$'}REPO
echo "${'$'}REPO was polluted during the build"
return 1
else
echo "${'$'}REPO does not exist"
fi"""
        }
    }

    triggers {
        vcs {
            quietPeriodMode = VcsTrigger.QuietPeriodMode.USE_CUSTOM
            quietPeriod = 90
            triggerRules = "-:design-docs"
        }
    }

    dependencies {
        dependency(Gradle_Branches_Checkpoints.buildTypes.Gradle_Branches_Checkpoints_Stage4BranchCoverage) {
            snapshot {
                onDependencyFailure = FailureAction.CANCEL
                onDependencyCancel = FailureAction.CANCEL
            }
        }
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux")
    }
})
