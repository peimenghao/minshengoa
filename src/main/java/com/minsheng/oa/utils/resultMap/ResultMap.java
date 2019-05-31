
package com.minsheng.oa.utils.resultMap;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @classDesc: 功能描述:(通用baseapi 父类)

 */

@Component
public class ResultMap {

	public Map<String, Object> resutError(String msg) {

		return resut(BaseApiConstants.HTTP_500_CODE, msg, null);   //    功能描述:(返回错误)

	}


	public Map<String, Object> resutSuccessDate(Object data) {
		return resut(BaseApiConstants.HTTP_200_CODE, BaseApiConstants.HTTP_SUCCESS_NAME, data);//  功能描述:(返回成功)
	}


	public Map<String, Object> resutSuccess() {
		return resut(BaseApiConstants.HTTP_200_CODE, BaseApiConstants.HTTP_SUCCESS_NAME, null);
	}


//
//	public Map<String, Object> resutError(String msg) {   // 功能描述:(400)
//		return resut(BaseApiConstants.HTTP_400_CODE, msg, null);
//	}

	public Map<String, Object> resutSuccess(String msg) {      //返回成功,无date

		return resut(BaseApiConstants.HTTP_200_CODE, msg, null);
	}



	/**
	 * 
	 * @methodDesc: 功能描述:(自定义返回)
	 */
	public Map<String, Object> resut(Integer code, String msg, Object data) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(BaseApiConstants.HTTP_CODE_NAME, code);
		result.put(BaseApiConstants.HTTP_200_NAME, msg);
		if (data != null)
			result.put(BaseApiConstants.HTTP_DATA_NAME, data);
		return result;
	}

}
