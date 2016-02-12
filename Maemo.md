# Introduction #

MicroEmu can be used in two different ways to allow Maemo users using Java ME software. First method uses Sun Java SE for Embedded which has limited 90 days license. Better option would be to use second method as it is a full open source solution, but at the moment it is possible to run only on x86 emulator. Future plans includes making the second method available also for Maemo devices. More details can be found in MaemoRoadmap document.

# First method #

Works either on device or in scratchbox (ARM)

**1. Create microemu-maemo development folder**
<pre>
mkdir microemu-maemo<br>
cd microemu-maemo<br>
</pre>

**2. Download and install Sun Java SE for Embedded** (http://java.sun.com/javase/downloads/embedded.jsp)

Java SE for Embedded 6 EABI, glibc 2.5, Hard Float (VFP), Little Endian

**3. Download and install MicroEmulator**
(http://snapshot.microemu.org/microemulator/download/)
<pre>
unzip microemulator-3.0.0-SNAPSHOT.zip<br>
</pre>

**4. And finally run the SimpleDemo MIDlet**
<pre>
java -cp microemulator-3.0.0-SNAPSHOT/microemulator.jar:microemulator-3.0.0-SNAPSHOT/apps/microemu-demo.jar org.microemu.app.Main org.microemu.midp.examples.simpledemo.SimpleDemoMIDlet<br>
</pre>

# Second method #

Works only in scratchbox (x86)

**1. Create microemu-maemo development folder**
<pre>
mkdir microemu-maemo<br>
cd microemu-maemo<br>
</pre>

**2. Download and install JamVM 1.4.5** (http://jamvm.sourceforge.net/)
<pre>
tar xjf jamvm-1.4.5.tar.bz2<br>
cd jamvm-1.4.5<br>
./configure<br>
make install<br>
cd ..<br>
</pre>

**3. Download and install Jikes 1.22** (http://jikes.sourceforge.net/)
<pre>
tar xjf jikes-1.22.tar.bz2<br>
cd jikes-1.22<br>
./configure<br>
make install<br>
cd ..<br>
</pre>

**4. Download and install GNU Classpath 0.93**
(http://www.gnu.org/software/classpath/)
<pre>
tar xzf classpath-0.93.tar.gz<br>
cd classpath-0.93<br>
./configure --with-jikes --disable-plugin<br>
make install<br>
cd ..<br>
</pre>

**5. Download and install MicroEmulator**
(http://snapshot.microemu.org/microemulator/download/)
<pre>
unzip microemulator-3.0.0-SNAPSHOT.zip<br>
</pre>

**6. And finally run the SimpleDemo MIDlet**
<pre>
jamvm -cp microemulator-3.0.0-SNAPSHOT/microemulator.jar:microemulator-3.0.0-SNAPSHOT/apps/microemu-demo.jar org.microemu.app.Main org.microemu.midp.examples.simpledemo.SimpleDemoMIDlet<br>
</pre>