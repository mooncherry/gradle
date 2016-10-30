package Gradle_Branches_Compatibility

import Gradle_Branches_Compatibility.buildTypes.*
import Gradle_Branches_Compatibility.vcsRoots.*
import Gradle_Branches_Compatibility.vcsRoots.Gradle_Branches_Compatibility_GradleCompatibilityVersionedSettingsVcs
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.Project
import jetbrains.buildServer.configs.kotlin.v10.projectFeatures.VersionedSettings
import jetbrains.buildServer.configs.kotlin.v10.projectFeatures.VersionedSettings.*
import jetbrains.buildServer.configs.kotlin.v10.projectFeatures.versionedSettings

object Project : Project({
    uuid = "6a760f70-09fc-4d3a-85f2-762c359c97e0"
    extId = "Gradle_Branches_Compatibility"
    parentId = "Gradle_Branches"
    name = "Compatibility"
    description = "runs compatibility checks to ensure gradle is working with other systems"

    vcsRoot(Gradle_Branches_Compatibility_GradleCompatibilityVersionedSettingsVcs)

    buildType(Gradle_Branches_Compatibility_ApiChangeReport)
    buildType(Gradle_Branches_Compatibility_ColonyCompatibility)

    features {
        versionedSettings {
            id = "PROJECT_EXT_63"
            mode = VersionedSettings.Mode.ENABLED
            buildSettingsMode = VersionedSettings.BuildSettingsMode.PREFER_SETTINGS_FROM_VCS
            rootExtId = Gradle_Branches_Compatibility_GradleCompatibilityVersionedSettingsVcs.extId
            showChanges = true
            settingsFormat = VersionedSettings.Format.KOTLIN
        }
    }
})
