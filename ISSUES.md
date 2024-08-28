
## Software versions
- **JVM:** 21.0.4
- **Gradle:** 8.5
- **Groovy:** 3.0.17


## Faced issues

- Not a lot of documentation about OpenSearch Plugin development
- Error `Found invalid file permissions:  Source file is executable:...` for several source files
  > **FIX:** `chmod -x` for each problematic source file. 
- Errors displayed by OpenSearch are not clear
  > Faced the error `illegal_argument_exception` because of missing `request.param("id")` on upgrade endpoint, 
  > I wasted a lot of time trying to figure it out by googling and doing a poor debug until I found the reason.
- 

