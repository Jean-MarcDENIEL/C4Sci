package c4sci.modelViewPresenterController.viewerPresenterInterface;
/**
 * This class encapsulates the processing of a change on to a Component.<br>
 * Its main interests are :
 * <ul>
 * <li> being able to clone itself : <b>prototype</b> GoF pattern,
 * <li> knowing the {@link ComponentChange} to work on.
 * </ul> 
 * <br>
 * This class is simply used by calling its {@link #run()} method.
 * @author jeanmarc.deniel
 *
 */
public abstract class ComponentChangeRunnable implements Runnable{
	private ComponentChange		compChange;
	@SuppressWarnings("unused")
	private ComponentChangeRunnable(){}
	public ComponentChangeRunnable(ComponentChange comp_change){
		compChange = comp_change;
	}
	public ComponentChange		getComponentChange(){
		return compChange;
	}
	/**
	 * The method to call in order to get the changes performed on the Component.
	 */
	//CHECKSTYLE:OFF
	public abstract void run();
	//CHECKSTYLE:ON
	/**
	 * Creates a {@link ComponentChangeRunnable} of the same type of "this".<br>
	 * <b>Pattern :</b> This method instantiates the <b>Prototype</b> GoF pattern.
	 * @param comp_change
	 * @return <i>null</i> if cannot create the adapted clone to comp_change, or the ComponentChangeRunnable to {@link #run()}.
	 */
	public abstract ComponentChangeRunnable clonePrototype(ComponentChange comp_change);
}
