You have successfully created an Atlassian Plugin!

Here are the SDK commands you'll use immediately:

* atlas-run   -- installs this plugin into the product and starts it on localhost
* atlas-debug -- same as atlas-run, but allows a debugger to attach at port 5005
* atlas-cli   -- after atlas-run or atlas-debug, opens a Maven command line window:
                 - 'pi' reinstalls the plugin into the running product instance
* atlas-help  -- prints description for all commands in the SDK
### 如果要导入到eclipse需要执行

```
atlas-mvn eclipse:eclipse
```
### 打包的话就执行

```
atlas-mvn package
```

执行完atlas-run 后会生成一个target目录，target目录下的`...SNAPSHOT.jar`这个包是整个插件打包后生成的，可以登录jira然后导入这个jar包就可以用这个插件了

Full documentation is always available at:

https://developer.atlassian.com/display/DOCS/Introduction+to+the+Atlassian+Plugin+SDK
