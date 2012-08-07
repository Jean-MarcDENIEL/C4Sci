/**
* This package provides a clean separation between layers of the Model-View-Presenter-Controller.
* <br>
* This four layer model avoids coupling between data treatment and view interfacing and presentation constraints.<br>
* To achieve this goal, each layer :
* <ul>
* <li>is managed by its pools of threads</li>
* <li>can only communicate with other layers through interfaces</li>
* </ul>
* These layers are the followings :
* <ol>
* <li><b>View : </b> This layer modifies the visual components. It is coupled with the underlying OS.</li>
* <li><b>Presenter :</b> This layer encapsulates the presentation strategies. It chooses what to do of ascending user messages and descending treatment results.</li>
* <li><b>Controller :</b> This layer encapsulated the treatment strategies. It chooses which treatment to perform according to ascending requests.</li>
* <li><b>Model :</b> This layer encapsulated the data treatments. It processed requested jobs and sends back job results.
* </ol>
* <br>
* The typical sequence diagram is this :<br>
* <img src="doc-files/4 layers jobs treatment sequence.jpg" width="100%">
*/
package c4sci.modelViewPresenterController;