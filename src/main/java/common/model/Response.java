package common.model;
/**
 * 
 * @author liangzhenghui
 *
 */
public class Response {
	//默认没错误
	private boolean error = false;
	// 默认为0正确,-1系统错误,其他见BizException中定义
	private String errorType = "0";
	private String errorMessage;
	private String errorDetail;
	private Object result;
	private Object result1;// 备用封装字段1
	private Object result2;// 备用封装字段1

	public Response() {
		super();
	}

	public Response(boolean error) {
		super();
		this.error = error;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Object getResult1() {
		return result1;
	}

	public void setResult1(Object result1) {
		this.result1 = result1;
	}

	public Object getResult2() {
		return result2;
	}

	public void setResult2(Object result2) {
		this.result2 = result2;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	

}
