# Ignore inline messages which lay outside a diff's range of PR
github.dismiss_out_of_range_messages

# PR check
warn("PRのサイズが大きいです。分割することをお勧めします。") if git.lines_of_code > 500

# AndroidLint
android_lint.filtering = true
Dir.glob("**/lint-results.xml").each { |report|
  android_lint.report_file = report.to_s
  android_lint.lint(inline_mode: true)
}

# ktlint
checkstyle_format.base_path = Dir.pwd
Dir.glob("**/ktlint*SourceSetCheck.xml").each { |report|
  checkstyle_format.report report.to_s
}
