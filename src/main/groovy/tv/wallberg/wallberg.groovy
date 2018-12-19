package tv.wallberg
import java.time.*
import java.time.temporal.ChronoUnit

import groovy.cli.commons.CliBuilder

def String[] args;

def cli = new CliBuilder(usage:'groovy wallberg')
cli.u(longOpt:'url', args:1, argName:'url', 'URL of the image to download')
cli.f(longOpt:'file', args:1, argName:'path', 'local file location of the image')
cli.h(longOpt:'help', 'print this message')
def options = cli.parse(args)
if ( !options ) return
if ( options.h ) { cli.usage() ; return }


/** Helper Class */
class FileBinaryCategory {

   /** define operator '<<' */
   def static leftShift(File file, URL url) {
     url.withInputStream { is->
         file.withOutputStream { os->
             def bs = new BufferedOutputStream( os )
             bs << is
         }
     }
  }

}

tempdirWin = "c:/temp"
tempdir = tempdirWin
String osName = System.getProperty("os.name").toLowerCase()
if (osName.contains("linux")) { tempdir = "/mnt/c/temp" }
println "tempdir is ${tempdir}" 

source = options.u ? options.u : "http://www.foto-webcam.org/webcam/wallberg/"
image = options.f ? options.f : "wallberg.jpg"
script = "setwallpaper.ps1"

// proxy settings
System.getProperties().put("proxySet", "true");
System.getProperties().put("proxyHost", "192.168.2.105");
System.getProperties().put("proxyPort", "3128");

/** download data from site */
LocalDateTime downloadData() {

  def zone = ZoneId.of("Europe/Berlin")
  def now = LocalDateTime.now(zone)
  def year = now.getYear()
  def month = LeadingZero.prepend(now.getMonth().getValue())
  def date = LeadingZero.prepend(now.getDayOfMonth())
  def hour = LeadingZero.prepend(now.getHour())
  def minute = now.getMinute()
  minute = LeadingZero.prepend(minute - (minute % 10))

  println "current download at ${now} in timezone ${zone}, date: ${year}-${month}-${date}, ${hour}:${minute}"

  def source = new URL( "${source}${year}/${month}/${date}/${hour}${minute}_hu.jpg" )
  def target = new File( "${tempdir}/${image}" )

  // download URL located image to local file  
  println "downloading: '${source}' -> '${target}' ... "
  target.delete()
  int i = 0
  while (true) {
    use( FileBinaryCategory ) {
      target << source
    }
    if ( target.length() > 2000) {
      println "done."
	  return now
    }
	if ( i >= 10) {
	  println "no image available, skipped."
	  return null
	}
  
    println "try again (${i}) ..."
    sleep(20000)
	i++
  }
}

/** set wallpaper on windows desktop */
void setWallpaper() {

  // extract powershell script from class path
  URL scriptUrl = this.getClass().getClassLoader().getResource("${script}")
  File scriptFile = new File("${tempdir}/${script}")
  println "extract script ${scriptUrl} to ${scriptFile} ..."
  use( FileBinaryCategory ) {
	  scriptFile << scriptUrl
  }
  println "done."
  
  // call powershell script to set wallpaper
  def scriptPs = "${tempdirWin}/${script}"
  def imagePs = "${tempdirWin}/${image}"
  println "set local file '${imagePs}' as wallpaper using script '${scriptPs}' ... "
  Process process = "powershell.exe -ExecutionPolicy Bypass -file  ${scriptPs} ${imagePs}".execute()
  int result = process.waitFor();
  if (result == 0)
	  println "done."
  else
	  println "error = ${result}"
}

/** wait until next image is available */
void delayExecution(LocalDateTime now) {

  // define "then" as "the last time with full 10 min. before now plus 10 min 30 sec."
  def then = now.minusMinutes(now.getMinute() % 10).plusMinutes(10).minusSeconds(now.getSecond()).plusSeconds(30)
  def delay = now.until(then, ChronoUnit.MILLIS)
  println "next download at ${then} i.e. in ${delay / 1000} sec. ... "
  sleep(delay);
}

// main loop ...
while (true) {
  // now == current dateTime if download was successful else now == null
  def now = downloadData()
  if (now != null) setWallpaper()
  delayExecution(now)
}
