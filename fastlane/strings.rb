# Strings file for Fastlane configuration
# This file contains all static strings used in Fastfile to avoid hardcoding

def slack_error_message(lane, exception)
  "❌ *#{ENV['APP_NAME']}* - #{lane} failed!\n```#{exception.message}```"
end

def current_branch_message(git_branch)
  "🌿 Current Branch: #{git_branch}"
end

def building_message(build_type)
  "👉 Building #{build_type}"
end

def firebase_fetch_error_message(e)
  "Could not fetch latest release from Firebase: #{e.message}"
end

def no_previous_release_message(current_vc, next_version_code)
  "No previous Firebase release found. Using current versionCode (#{current_vc}) + 1 = #{next_version_code}"
end

def version_code_not_found_message(file_path)
  "Could not find VERSION_CODE in #{file_path}"
end

def version_code_updated_message(new_vc, file_path)
  "Updated VERSION_CODE to #{new_vc} in #{file_path}"
end

def missing_env_var_message(var)
  "Missing ENV[#{var}]"
end

def no_apk_found_message
  "Could not find any APK to distribute. Please check if the build succeeded."
end

def apk_not_found_message(apk_path)
  "APK not found at #{apk_path}. Build may have failed or path is incorrect."
end

def distributing_apk_message(app_id)
  "🚀 Distributing APK for App ID: #{app_id}"
end

def no_release_notes_message
  "No release notes provided."
end

def build_icon_staging
  "🐞"
end

def build_icon_release
  "🚀"
end

def build_icon_default
  "📱"
end

def release_notes_formatted_message(build_type, flavor, app_name, version, git_branch, release_notes)
  icon = case build_type
         when "staging" then build_icon_staging
         when "release" then build_icon_release
         else build_icon_default
         end
  "#{icon} #{app_name} v#{version} (#{flavor}-#{build_type})\nBranch: #{git_branch}\n\n#{release_notes}"
end

def slack_success_message(build_type, flavor, app_name, version)
  icon = case build_type
         when "staging" then build_icon_staging
         when "release" then build_icon_release
         else build_icon_default
         end
  "#{icon} #{app_name} v#{version} (#{flavor}-#{build_type}) ✅"
end
