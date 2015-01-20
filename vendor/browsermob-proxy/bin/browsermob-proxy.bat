@REM ----------------------------------------------------------------------------
@REM Copyright 2001-2004 The Apache Software Foundation.
@REM
@REM Licensed under the Apache License, Version 2.0 (the "License");
@REM you may not use this file except in compliance with the License.
@REM You may obtain a copy of the License at
@REM
@REM      http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing, software
@REM distributed under the License is distributed on an "AS IS" BASIS,
@REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM See the License for the specific language governing permissions and
@REM limitations under the License.
@REM ----------------------------------------------------------------------------
@REM

@echo off

set ERROR_CODE=0

:init
@REM Decide how to startup depending on the version of windows

@REM -- Win98ME
if NOT "%OS%"=="Windows_NT" goto Win9xArg

@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" @setlocal

@REM -- 4NT shell
if "%eval[2+2]" == "4" goto 4NTArgs

@REM -- Regular WinNT shell
set CMD_LINE_ARGS=%*
goto WinNTGetScriptDir

@REM The 4NT Shell from jp software
:4NTArgs
set CMD_LINE_ARGS=%$
goto WinNTGetScriptDir

:Win9xArg
@REM Slurp the command line arguments.  This loop allows for an unlimited number
@REM of arguments (up to the command line limit, anyway).
set CMD_LINE_ARGS=
:Win9xApp
if %1a==a goto Win9xGetScriptDir
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto Win9xApp

:Win9xGetScriptDir
set SAVEDIR=%CD%
%0\
cd %0\..\.. 
set BASEDIR=%CD%
cd %SAVEDIR%
set SAVE_DIR=
goto repoSetup

:WinNTGetScriptDir
set BASEDIR=%~dp0\..

:repoSetup


if "%JAVACMD%"=="" set JAVACMD=java

if "%REPO%"=="" set REPO=%BASEDIR%\lib

set CLASSPATH="%BASEDIR%"\etc;"%REPO%"\slf4j-jdk14-1.5.3.jar;"%REPO%"\slf4j-api-1.5.3.jar;"%REPO%"\sitebricks-0.8.3.jar;"%REPO%"\mvel2-2.0.18.jar;"%REPO%"\guava-r07.jar;"%REPO%"\annotations-7.0.3.jar;"%REPO%"\commons-httpclient-3.1.jar;"%REPO%"\commons-lang-2.5.jar;"%REPO%"\dom4j-1.6.1.jar;"%REPO%"\jaxen-1.1.1.jar;"%REPO%"\jdom-1.0.jar;"%REPO%"\xom-1.0.jar;"%REPO%"\xmlParserAPIs-2.6.2.jar;"%REPO%"\xalan-2.6.0.jar;"%REPO%"\icu4j-2.6.1.jar;"%REPO%"\saxpath-1.0-FCS.jar;"%REPO%"\servlet-api-2.5.jar;"%REPO%"\xstream-1.3.1.jar;"%REPO%"\xpp3_min-1.1.4c.jar;"%REPO%"\jsoup-1.4.1.jar;"%REPO%"\freemarker-2.3.10.jar;"%REPO%"\guice-multibindings-3.0.jar;"%REPO%"\jackson-core-asl-1.7.1.jar;"%REPO%"\jackson-mapper-asl-1.7.1.jar;"%REPO%"\httpclient-4.2.3.jar;"%REPO%"\httpcore-4.2.2.jar;"%REPO%"\commons-logging-1.1.1.jar;"%REPO%"\commons-codec-1.6.jar;"%REPO%"\httpmime-4.2.3.jar;"%REPO%"\commons-io-1.3.2.jar;"%REPO%"\jopt-simple-3.2.jar;"%REPO%"\ant-1.8.2.jar;"%REPO%"\ant-launcher-1.8.2.jar;"%REPO%"\bcprov-jdk15on-1.47.jar;"%REPO%"\jetty-server-7.3.0.v20110203.jar;"%REPO%"\jetty-continuation-7.3.0.v20110203.jar;"%REPO%"\jetty-http-7.3.0.v20110203.jar;"%REPO%"\jetty-io-7.3.0.v20110203.jar;"%REPO%"\jetty-util-7.3.0.v20110203.jar;"%REPO%"\jetty-servlet-7.3.0.v20110203.jar;"%REPO%"\jetty-security-7.3.0.v20110203.jar;"%REPO%"\guice-3.0.jar;"%REPO%"\javax.inject-1.jar;"%REPO%"\aopalliance-1.0.jar;"%REPO%"\guice-servlet-3.0.jar;"%REPO%"\jcip-annotations-1.0.jar;"%REPO%"\selenium-api-2.29.1.jar;"%REPO%"\json-20080701.jar;"%REPO%"\browsermob-proxy-2.0-beta-8.jar
set EXTRA_JVM_ARGUMENTS=
goto endInit

@REM Reaching here means variables are defined and arguments have been captured
:endInit

%JAVACMD% %JAVA_OPTS% %EXTRA_JVM_ARGUMENTS% -classpath %CLASSPATH_PREFIX%;%CLASSPATH% -Dapp.name="browsermob-proxy" -Dapp.repo="%REPO%" -Dbasedir="%BASEDIR%" net.lightbody.bmp.proxy.Main %CMD_LINE_ARGS%
if ERRORLEVEL 1 goto error
goto end

:error
if "%OS%"=="Windows_NT" @endlocal
set ERROR_CODE=1

:end
@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" goto endNT

@REM For old DOS remove the set variables from ENV - we assume they were not set
@REM before we started - at least we don't leave any baggage around
set CMD_LINE_ARGS=
goto postExec

:endNT
@endlocal

:postExec

if "%FORCE_EXIT_ON_ERROR%" == "on" (
  if %ERROR_CODE% NEQ 0 exit %ERROR_CODE%
)

exit /B %ERROR_CODE%
