package org.zh.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Description:TODO(DataTable 请求数据格式封装)   
 * @author: level.meng 
 * @date:   2017年2月16日 下午4:45:25   
 * @param <T>
 * @param <K>  
 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
@SuppressWarnings("all")
@Component
public class DataTableJSONResponse<T,K> extends CustomResponse{
	
	
	private static final long serialVersionUID = -1846559783490197193L;
	
	private int draw;
	private Long recordsTotal;
	private Long recordsFiltered;
	private List<T> data;


	
	public DataTableJSONResponse() {
		// TODO Auto-generated constructor stub
		super(STATUS_SUCCESS, SUCCESS);
	}
	
	public DataTableJSONResponse(int draw,Long recordsTotal,Long recordsFiltered) {
		// TODO Auto-generated constructor stub
		this.draw =  draw;
		this.recordsTotal =  recordsTotal;
		this.recordsFiltered =  recordsFiltered;
		this.data =  null;
	}
	
	public DataTableJSONResponse(int draw,Long recordsTotal,Long recordsFiltered,List<T> data) {
		// TODO Auto-generated constructor stub
		this.draw =  draw;
		this.recordsTotal =  recordsTotal;
		this.recordsFiltered =  recordsFiltered;
		this.data =  data;
	}
	
	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public Long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
		
	}
	
	@Override
	@JsonIgnore
	public Map<String, Object> getValues() {
		// TODO Auto-generated method stub
		return super.getValues();
	}
	
	public String toJSONString(int draw,Long recordsTotal,Long recordsFiltered,List<T> list) {
		// TODO Auto-generated constructor stub
		this.draw =  draw;
		this.recordsTotal =  recordsTotal;
		this.recordsFiltered =  recordsFiltered;
		this.data =  list;
		return super.toJSONString();
	}
	
	public String toJSONString(int draw,Long recordsTotal,Long recordsFiltered,List<T> list,Map<Class<?>, Class<?>> sourceMixins) {
		// TODO Auto-generated constructor stub
		this.draw =  draw;
		this.recordsTotal =  recordsTotal;
		this.recordsFiltered =  recordsFiltered;
		this.data =  list;
		return super.toJSONString(sourceMixins);
	}
	
	
	
}
