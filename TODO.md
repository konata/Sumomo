### correctness
1. chars are classified into `braced`, `normal`, `literal` groups

### performance
1. flat Sequential and Alternative to Regexp with more information

### character classes
1. ^-]\   meta

### Issue
1. alternative should use right association (DONE)
2. sequential should use right association  (DONE)
3. reduce pass and fail for unnecessary sequential and alternative node (DONE)
4. range should be expressed lazily
5. support `.` literal in []
6. backtrace when IndexOutOfRange happen in subroutine



