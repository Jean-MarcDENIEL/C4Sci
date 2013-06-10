/**
 * This package is intended at giving useful data management abilities.<br>
 * <b>Warning : use of String values</b>
 * <ul>
 * 	<li> Data value is represented as Strings </li>
 * 	<li> Data parameter agregates have their values separated by whitespaces. E.g "1 2 3"</li>
 *  <li> That's why using whitespaces as part of a parameter inner state will create parsing issues.
 * </ul>
 * 
 * The class diagram is the following :
 * <img src="doc-files/classes-diagram.png" width="100%">
 * 
 * Example of {@link c4sci.data.HierarchicalData} usage :<br>
 * 
 * <pre><code>
 *
 * 
 * class MyData extends {@link c4sci.data.HierarchicalData} {
 *  private {@link c4sci.data.dataParameters.GenericDataParameter}&ltModifiableFloat&gtmyInnerFloatState;
 *  private static final String                     FLOAT_STATE_TOKEN = "floatState";
 *  private static final InternationalizableTerm    FLOAT_STATE_NAME = new ..... ;
 *  private static final InternationalizableTerm    FLOAT_STATE_DESCR = new ......;
 *  
 *  private {@link c4sci.data.HierarchicalData}     oneSubData;
 *  private {@link c4sci.data.HierarchicalData}     anotherSubData;
 *  
 *  private static final String                     ONE_SUBDATA_TOKEN = "....";
 *  private static final String                     ANOTHER_SUBDATA_TOKEN = " .....";
 *  (so on with sub data terms ...)
 *  
 *  public void setMyInnerFloatState(float float_value){
 *   myInnerFloatState.accessValues().setFloatValue(float_value);
 *  }
 *  public float getMyInnerFloatState(){
 *      return myInnerFloatState.accessValue().getFloatValue();
 *  }
 * 
 *  public MyData(String data_token, InternationalizableTerm data_name, InternationalizableTerm data_description) throws CannotInstantiateDataException{
 *      super(data_token, data_name, data_description);
 *      //
 *      // managing data parameters
 *      //
 *      myInnerFloatState = new {@link c4sci.data.dataParameters.GenericArrayDataParameter}&ltModifiableFloat&gt(new ModifiableFloat(), FLOAT_STATE_TOKEN, FLOAT_STATE_NAME, FLOAT_STATE_DESCR);
 *      addDataParameter(myInnerFloatState);
 *      (so on with DataParameters) ...
 *  
 *  }
 * 
 *  &#64Override
 *  public HierarchialDataFactory getSubdataFactory() throws CannotInstantiateDataException {
 *      HierarchialDataFactory _res = super.getSubdataFactory();
 *      try{
 *          _res.addFactoringAbility(new OneSubHierarchicalDataClass( <i>token and term ...</i>, ONE_SUBDATA_TOKEN));
 *          _res.addFactoringAbility(new AnotherSubHierarchicalDataClass(<i>token and term ...</i>, ANOTHER_SUBDATA_TOKEN));
 *      }
 *      catch(CannotInstantiateParameterException _e){
 *          throw new CannotInstantiateDataException(getParentData(), getDataToken(), "sub data creation", _e);
 *      }
 *      return _res;		
 *  }
 *   
 *  &#64Override
 *  public PrototypeData newInstance() throws CannotInstantiateDataException {
 *      return new MyData();
 *  }
 * }
 * </code></pre>
*/

package c4sci.data;

