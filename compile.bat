@echo off
setlocal enabledelayedexpansion

REM Configurar rutas
set JAVA_HOME=C:\Program Files\Java\jdk-17
set TOMCAT_HOME=C:\Program Files\Apache Software Foundation\Tomcat 10.1
set PROJECT_DIR=%~dp0
set SRC_DIR=%PROJECT_DIR%src
set BIN_DIR=%PROJECT_DIR%bin
set LIB_DIR=%PROJECT_DIR%lib
set WEBCONTENT_DIR=%PROJECT_DIR%WebContent

REM Crear directorio bin si no existe
if not exist "%BIN_DIR%" mkdir "%BIN_DIR%"

REM Limpiar compilaciones anteriores
echo Limpiando compilaciones anteriores...
del /Q "%BIN_DIR%\com\universidad\*.class" 2>nul
for /d /r "%BIN_DIR%\com" %%d in (*) do @if exist "%%d" rmdir /s /q "%%d" 2>nul

REM Definir classpath
set CLASSPATH=%BIN_DIR%
set CLASSPATH=!CLASSPATH!;%LIB_DIR%\mysql-connector-j-8.0.33\mysql-connector-j-8.0.33.jar
set CLASSPATH=!CLASSPATH!;%TOMCAT_HOME%\lib\servlet-api.jar
set CLASSPATH=!CLASSPATH!;%TOMCAT_HOME%\lib\jsp-api.jar
set CLASSPATH=!CLASSPATH!;%TOMCAT_HOME%\lib\jstl.jar

echo.
echo ========================================
echo Compilando proyecto Universidad...
echo ========================================
echo JAVA_HOME: %JAVA_HOME%
echo TOMCAT_HOME: %TOMCAT_HOME%
echo SRC_DIR: %SRC_DIR%
echo BIN_DIR: %BIN_DIR%
echo.

REM Compilar código Java
"%JAVA_HOME%\bin\javac.exe" ^
  -d "%BIN_DIR%" ^
  -cp "!CLASSPATH!" ^
  -encoding UTF-8 ^
  "%SRC_DIR%\com\universidad\Main.java" ^
  "%SRC_DIR%\com\universidad\controller\AlumnoServlet.java" ^
  "%SRC_DIR%\com\universidad\dao\AlumnoDAO.java" ^
  "%SRC_DIR%\com\universidad\dao\CarreraDAO.java" ^
  "%SRC_DIR%\com\universidad\dao\CursoDAO.java" ^
  "%SRC_DIR%\com\universidad\dao\MatriculaDAO.java" ^
  "%SRC_DIR%\com\universidad\dao\ConexionDB.java" ^
  "%SRC_DIR%\com\universidad\model\Alumno.java" ^
  "%SRC_DIR%\com\universidad\model\Carrera.java" ^
  "%SRC_DIR%\com\universidad\model\Curso.java" ^
  "%SRC_DIR%\com\universidad\model\CursoCarrera.java" ^
  "%SRC_DIR%\com\universidad\model\Matricula.java" ^
  "%SRC_DIR%\com\universidad\model\Nota.java" ^
  "%SRC_DIR%\com\universidad\model\Persona.java" ^
  "%SRC_DIR%\com\universidad\model\Profesor.java" ^
  "%SRC_DIR%\com\universidad\model\Universidad.java" ^
  "%SRC_DIR%\com\universidad\service\UniversidadService.java" ^
  "%SRC_DIR%\com\universidad\util\FormatoUtil.java" ^
  "%SRC_DIR%\com\universidad\util\Validador.java"

if !errorlevel! equ 0 (
    echo.
    echo ========================================
    echo Compilacion exitosa!
    echo ========================================
    
    REM Crear WAR para Tomcat (opcional)
    if exist "%WEBCONTENT_DIR%" (
        echo.
        echo Preparando aplicacion web...
        if not exist "%WEBCONTENT_DIR%\WEB-INF\classes" mkdir "%WEBCONTENT_DIR%\WEB-INF\classes"
        xcopy "%BIN_DIR%\com" "%WEBCONTENT_DIR%\WEB-INF\classes\com" /E /I /Y >nul
        echo Archivos copiados a WEB-INF\classes
    )
) else (
    echo.
    echo ========================================
    echo ERROR: Compilacion fallida!
    echo ========================================
    pause
    exit /b 1
)

pause
endlocal