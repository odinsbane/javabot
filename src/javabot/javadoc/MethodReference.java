package javabot.javadoc;

import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ExecutableMemberDoc;
import com.sun.javadoc.Parameter;

import org.jdom.Element;

public class MethodReference
{
	private ClassReference owner;
	private String methodName;
	private String longSignatureTypes;
	private String shortSignatureTypes;

	public MethodReference(ExecutableMemberDoc doc, ClassReference owner)
	{
		this.owner=owner;
		methodName=doc.name();
		Parameter[] parameters=doc.parameters();
		StringBuffer longTypes=new StringBuffer();
		StringBuffer shortTypes=new StringBuffer();
		
		for (int i=0;i<parameters.length;i++)
		{
			Parameter parameter=parameters[i];
			
			if (i>0)
			{
				longTypes.append(", ");
				shortTypes.append(", ");
			}
			
			longTypes.append
			(
				parameter.type().qualifiedTypeName()+
				parameter.type().dimension()
			);
			
			shortTypes.append
			(
				parameter.type().typeName()+
				parameter.type().dimension()
			);
		}
		
		longSignatureTypes=longTypes.toString();
		shortSignatureTypes=shortTypes.toString();
	}

	public boolean signatureMatches(String signature)
	{
		String longTypes=methodName+"("+longSignatureTypes+")";
		String shortTypes=methodName+"("+shortSignatureTypes+")";
		
		return
			signature.equals(longTypes) ||
			signature.equalsIgnoreCase(shortTypes);
	}

	public String getLongSignature()
	{
		return methodName+"("+longSignatureTypes+")";
	}

	public String getShortSignature()
	{
		return methodName+"("+shortSignatureTypes+")";
	}

	public String getMethodUrl(String baseUrl)
	{
		return
			owner.getQualifiedName()+
			"."+
			getLongSignature()+
			": "+
			owner.getClassHTMLPage(baseUrl)+
			"#"+
			encode(getLongSignature());
	}

	private String encode(String text)
	{
		return text.replaceAll(" ","%20");
	}

	public String getMethodName()
	{
		return methodName;
	}

	public MethodReference(Element element,ClassReference owner)
	{
		this.owner=owner;
		methodName=element.getAttribute("methodName").getValue();
		
		longSignatureTypes=element.getChildText("LongSignatureTypes");
		shortSignatureTypes=element.getChildText("ShortSignatureTypes");
	}

	public int getArgumentCount()
	{
		// This could be implemented better
		if (shortSignatureTypes.length()==0)
			return 0;
		
		char[] sig=shortSignatureTypes.toCharArray();
		int argCount=1;
		
		for (int i=0;i<sig.length;i++)
			if (sig[i] == ',')
				argCount++;
		
		return argCount;
	}

	public Element buildElement()
	{
		Element element=new Element("Method");
		element.setAttribute("methodName",methodName);
		
		Element longSignature=new Element("LongSignatureTypes");
		longSignature.setText(longSignatureTypes);
		
		Element shortSignature=new Element("ShortSignatureTypes");
		shortSignature.setText(shortSignatureTypes);
		
		element.addContent(shortSignature);
		element.addContent(longSignature);
		return element;
	}
}
