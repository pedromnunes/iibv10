package ao.atlantico.service;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;

public class GetNumberToWord_Response_Message extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		MbMessage inMessage = inAssembly.getMessage();
		MbMessageAssembly outAssembly = null;
		try {
			// create new message as a copy of the input
			MbMessage outMessage = new MbMessage(inMessage);
			outAssembly = new MbMessageAssembly(inAssembly, outMessage);
			// ----------------------------------------------------------
			// Add user code below
			
			MbElement inRoot = inAssembly.getMessage().getRootElement();
			
			MbElement input = inRoot.getFirstElementByPath("SOAP/");
			
			// montar mensagem de output
			MbElement outRoot = outAssembly.getMessage().getRootElement().getLastChild();
			
			MbElement numberToWords = outRoot.createElementAsLastChild(MbElement.TYPE_NAME, "GetNumberToWordResponse", null);
			
			numberToWords.setNamespace("http://CallSOAPServiceWithJavaNode");
			
			MbElement ubiNum = numberToWords.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "output", input.getName());
			
			outRoot.getFirstChild().detach();

			// End of user code
			// ----------------------------------------------------------
		} catch (MbException e) {
			// Re-throw to allow Broker handling of MbException
			throw e;
		} catch (RuntimeException e) {
			// Re-throw to allow Broker handling of RuntimeException
			throw e;
		} catch (Exception e) {
			// Consider replacing Exception with type(s) thrown by user code
			// Example handling ensures all exceptions are re-thrown to be handled in the flow
			throw new MbUserException(this, "evaluate()", "", "", e.toString(),
					null);
		}
		// The following should only be changed
		// if not propagating message to the 'out' terminal
		out.propagate(outAssembly);

	}

}
