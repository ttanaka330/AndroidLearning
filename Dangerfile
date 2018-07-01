# Ignore inline messages which lay outside a diff's range of PR
github.dismiss_out_of_range_messages

# PR check
warn("PRのサイズが大きいです。分割することをお勧めします。") if git.lines_of_code > 10

# ktlint
checkstyle_format.base_path = Dir.pwd
checkstyle_format.report "app/build/reports/ktlint/ktlint-debug.xml"

# AndroidLint
android_lint.report_file = "app/build/reports/lint-results.xml"
android_lint.severity = "Error"
android_lint.lint
