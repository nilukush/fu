# fu
Upload files to dropbox in account, nilukush

## HOW TO upload
* `git clone git@github.com:nilukush/fu.git`
* `./gradlew build`
* `./gradlew run -Pfp=<list of file path>`
    * Currently, allows only one file to be uploaded

## Example
* `./gradlew run -Pfp="['/Users/nilesh.kumar/Downloads/Spring_Boot_in_Action.pdf']"`

## Resources
* [Gradle Tasks](https://docs.gradle.org/current/userguide/more_about_tasks.html)
* [HOW TO pass CL args to Gradle](https://stackoverflow.com/questions/11696521/how-to-pass-arguments-from-command-line-to-gradle)
* [Create Java Application using Gradle](https://guides.gradle.org/building-java-applications/)
* [Apache Commons Lang](https://commons.apache.org/proper/commons-lang/)
* [Dropbox Java SDK](https://github.com/dropbox/dropbox-sdk-java)