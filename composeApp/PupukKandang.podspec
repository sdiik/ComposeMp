Pod::Spec.new do |spec|
    spec.name                     = 'PupukKandang'
    spec.version                  = '1.0.5'
    spec.license                  = { :type => 'MIT', :file => 'LICENSE' }
    spec.homepage                 = 'Link to a Kotlin/Native module'
    spec.authors                  = { 'sdiik' => 'ahmadshiddiq0@gmail.com' }
    spec.source                   = { :git => 'https://github.com/sdiik/ComposeMp.git', :tag => spec.gversion.to_s }
    spec.summary                  = 'Some description for a Kotlin/Native module'
    spec.libraries                = 'c++'


                
    if !Dir.exist?('build/cocoapods/framework/PupukKandang.framework') || Dir.empty?('build/cocoapods/framework/PupukKandang.framework')
        raise "

        Kotlin framework 'PupukKandang' doesn't exist yet, so a proper Xcode project can't be generated.
        'pod install' should be executed after running ':generateDummyFramework' Gradle task:

            ./gradlew :composeApp:generateDummyFramework

        Alternatively, proper pod installation is performed during Gradle sync in the IDE (if Podfile location is set)"
    end
                
    spec.xcconfig = {
        'ENABLE_USER_SCRIPT_SANDBOXING' => 'NO',
    }
                
    spec.pod_target_xcconfig = {
        'KOTLIN_PROJECT_PATH' => ':composeApp',
        'PRODUCT_MODULE_NAME' => 'PupukKandang',
    }
                
    spec.script_phases = [
        {
            :name => 'Build PupukKandang',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                if [ "YES" = "$OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED" ]; then
                  echo "Skipping Gradle build task invocation due to OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED environment variable set to \"YES\""
                  exit 0
                fi
                set -ev
                REPO_ROOT="$PODS_TARGET_SRCROOT"
                "$REPO_ROOT/../gradlew" -p "$REPO_ROOT" $KOTLIN_PROJECT_PATH:syncFramework \
                    -Pkotlin.native.cocoapods.platform=$PLATFORM_NAME \
                    -Pkotlin.native.cocoapods.archs="$ARCHS" \
                    -Pkotlin.native.cocoapods.configuration="$CONFIGURATION"
            SCRIPT
        }
    ]
    spec.resources = ['build/compose/cocoapods/compose-resources']
end