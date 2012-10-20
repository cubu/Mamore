Mahout-MongoDB-Recommender
================

Recommender (user/item) system using Mahout and MongoDB, wrapped by a REST service.

Contributing
------------

If you make improvements to this application, please share with others.

Send the author a message, create an [issue]( https://github.com/fertapric/Mamore/issues ), or fork the project and submit a pull request.

If you add functionality to this application, create an alternative implementation, or build an application that is similar, please contact me and I'll add a note to the README so that others can find your work.

REST service details
--------------------

The root path for the resources is: `http://<tomcatHost>:<TomcatPort>/Recommender<url-pattern>`
The `<url-pattern>` is specified in the url-pattern tag in the conf/web.xml file, and by default it is set to /*

### Recommendations resources ###

For a particular user, you can request:

The recommended users with an HTTP GET to `http://<rootPath>/users/<userID>/recommendedUsers` , receiving an XML response like this:

      <?xml version="1.0" encoding="UTF-8"?>
      <recommendedUsers type="array">
        <recommendedUser>
          <id>id of the recommended user #1</id>
        </recommendedUser>
        <recommendedUser>
          <id>id of the recommended user #2</id>
        </recommendedUser>
        <recommendedUser>
          <id>id of the recommended user #3</id>
        </recommendedUser>
        .
        .
        .
      </recommendedUsers>

The recommended items with an HTTP GET to `http://<rootPath>/users/<userID>/recommendedItems` , receiving an XML response like this:

      <?xml version="1.0" encoding="UTF-8"?>
      <recommendedItems type="array">
        <recommendedItem>
          <id>id of the recommended item #1</id>
        </recommendedItem>
        <recommendedItem>
          <id>id of the recommended item #2</id>
        </recommendedItem>
        <recommendedItem>
          <id>id of the recommended item #3</id>
        </recommendedItem>
        .
        .
        .
      </recommendedItems>

### Preferences resources ###

You can create a preference with an HTTP POST to `http://<rootPath>/recommender_preferences.xml` , including the data of the preference in the body in an XML like this:

      <?xml version="1.0" encoding="UTF-8"?>
      <recommender-preference>
        <user-id type="integer">id of the user to which the preference belongs</user-id>
        <item-id type="integer">id of the item to which the preference refers</item-id>
        <preference type="integer">numeric value of the preference</preference>
      </recommender-preference>

You can delete a preference with an HTTP DELETE to `http://<rootPath>/recommender_preferences/<preferenceID>.xml`

Credits
-------

This project has been developed at [Paradigma Labs]( http://labs.paradigmatecnologico.com/ )

The Recommender itself and the Mahout data model for MongoDB have been developed by Fernando Tapia Rico ( <http://fernandotapiarico.com/> )

The REST service wrapper has been developed by Álvaro Martín Fraguas ( <https://github.com/amartinfraguas> )

Collaborators
-------------

Julio Manuel Gonzalez Rodriguez 

Issues
------

Any issues? Please create an [Issue]( https://github.com/fertapric/Mamore/issues ) on GitHub.

License
-------

### Public Domain Dedication ###

This work is a compilation and derivation from other previously released works. With the exception of various included works, which may be restricted by other licenses, the author or authors of this code dedicate any and all copyright interest in this code to the public domain. We make this dedication for the benefit of the public at large and to the detriment of our heirs and successors. We intend this dedication to be an overt act of relinquishment in perpetuity of all present and future rights to this code under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

