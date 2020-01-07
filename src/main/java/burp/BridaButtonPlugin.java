package burp;

import javax.swing.JPanel;

public class BridaButtonPlugin extends CustomPlugin {
	
	private JPanel buttonPanel;
	private DefaultHook hookOrFunction;
	
	public BridaButtonPlugin(int platform, boolean isInterceptorHook,
			BurpExtender mainPlugin, String customPluginName, String customPluginExportedFunctionName,
			CustomPluginExecuteOnValues customPluginExecuteOn, String customPluginExecuteOnButtonName,
			CustomPluginParameterValues customPluginParameter,
			String customPluginParameterString, CustomPluginEncodingValues customPluginParameterEncoding,
			CustomPluginFunctionOutputValues customPluginFunctionOutput, String customPluginFunctionOutputString,
			CustomPluginEncodingValues customPluginOutputEncoding,
			CustomPluginEncodingValues customPluginOutputDecoding) {
		super(mainPlugin, customPluginName, customPluginExportedFunctionName, customPluginExecuteOn, customPluginExecuteOnButtonName,
				null, null, customPluginParameter,
				customPluginParameterString, customPluginParameterEncoding, customPluginFunctionOutput,
				customPluginFunctionOutputString, customPluginOutputEncoding, customPluginOutputDecoding);
		
		if(isInterceptorHook) {			
			// It is not possible to pass parameters to hooks
			hookOrFunction = new DefaultHook(customPluginExecuteOnButtonName,platform,customPluginExportedFunctionName,isInterceptorHook,new String[] {},null,false);
		} else if(customPluginParameter == CustomPlugin.CustomPluginParameterValues.POPUP) {
			hookOrFunction = new DefaultHook(customPluginExecuteOnButtonName,platform,customPluginExportedFunctionName,isInterceptorHook,new String[] {},customPluginParameterEncoding,true);
		} else {
			hookOrFunction = new DefaultHook(customPluginExecuteOnButtonName,platform,customPluginExportedFunctionName,isInterceptorHook,getParametersCustomPlugin(null,false),customPluginParameterEncoding,false);
		}
		this.setType(CustomPlugin.CustomPluginType.JBUTTON);
		
	}
	
	@Override
	public void enable() {
		buttonPanel = getMainPlugin().addButtonToHooksAndFunctions(hookOrFunction);
		setOnOff(true);
		
	}

	@Override
	public void disable() {
		if(isOn()) {
			// Disabling can fail for hooks if application is started. In this case the plugin should remain enabled.
			if(getMainPlugin().removeButtonFromHooksAndFunctions(buttonPanel, hookOrFunction)) {
				setOnOff(false);
			}
		}
	}

}