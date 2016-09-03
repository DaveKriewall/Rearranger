Contains code for Rearranger plugin for IntelliJ IDEA.

Some notes on the code:

1) This code was "degenerated" by IDEA's degenerate.bat procedure.  This was done because I
had converted all the code to use JDK 5.0 generics and other constructs.  Now that plugins can use Java 1.5 constructs
I have generified the code in most places.

2) I was in the middle of developing a new way of handling getters and setters.  The new way would
allow the user to associate arbitrarily-named setters and getters.  All of the UI is implemented, but
the pane is commented out so it won't appear.  None of the settings work (to save and restore) is
done, and none of the integration of this with the current getter/setter code.

3) I try to keep a list of all the outstanding requests in a "to-do" file, but I'm sure I have forgotten
to remove some that are implemented and have not included everything requested of me.

4) As of IDEA 9.0, the unit tests are all running again.  Thanks to the JetBrains folks for open sourcing the
community edition of their wonderful editor; it allowed me to peek inside and find out what they changed in IDEA 8.0
that made some unit tests fail.

5) This is how the unit tests operate: basically, I spin up enough of IDEA to open an editor with a sample Java class,
rearrange it according to specific rules, and then compare the result against a known result class.
In order to get the unit tests to compile, you must manually add idea.jar to the IDEA plugin SDK.  Don't ask me why
they don't do it for you.  They include just about every other jar they ship. :-)

6) Use the ant targets in Rearranger.xml to compile and build the plugin.  They copy the necessary .png and default
configuration XML files to the correct places.  The old files x.bat aren't needed any more.

7) If you need to change this code, please notify me at dave.kriewall <at> gmail.com so I'm aware and can keep in sync,
or even give you suggestions about how to go about it.

Thanks for your interest,
-Dave Kriewall
Attachmate, Inc. (formerly WRQ, Inc.)
1500 Dexter Ave N.
Seattle, WA 98109
USA
(206) 217-7065

PS. As of release of 3.7.x, there were 10,691 downloads of the plugin.
                     3.8.x, 10,798.
                     3.9.x, 11,644.
                     4.3.x, 16,316.
                     4.5,   22,995.    (IDEA 5.0)
                     4.8,   32,559 on 8/30/2008.
                     5.0,   39,579 on 1/29/2010.   (IDEA 9.0)
                     5.4,   47,035 on 5/21/2011.   (IDEA 10.5)