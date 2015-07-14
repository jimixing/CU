package com.ebao.gs.pol.prdt.config.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ebao.gs.pol.prdt.config.dao.PageNameDao;
import com.ebao.pub.util.DBean;

public class PageNameDaoImpl implements PageNameDao {
	
	@Override
	public String findPageNameDescByCode(String pageNameCode) throws Exception {
		// TODO Auto-generated method stub
		DBean db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			db = new DBean();
			db.connect();
			Connection conn = db.getConnection();
			
			List<Object> sqlParams = new ArrayList<Object>();
			String sql = this.getFindPageNameDescByCodeQuerySql(sqlParams, pageNameCode);
			
			ps = conn.prepareStatement(sql);
			DBean.setParameters(ps, sqlParams);
			rs = ps.executeQuery();
			
			String pageNameDesc = null;
			while (rs.next()){
				pageNameDesc = rs.getString("page_name_desc");
			}
			return pageNameDesc;
		} catch (Exception e) {
			throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
	}
	
	private String getFindPageNameDescByCodeQuerySql(List<Object> sqlParams, String pageNameCode) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(" select page_name_desc from t_uiconfig_page_name where 1=1 ");
		
		if (StringUtils.isNotEmpty(pageNameCode)) {
			sb.append(" and page_name_code = ?");
			sqlParams.add(pageNameCode);
		}
		
		return sb.toString();
	}

	@Override
	public String findPageNameCodeByDesc(String pageNameDesc) throws Exception {
		// TODO Auto-generated method stub
		DBean db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			db = new DBean();
			db.connect();
			Connection conn = db.getConnection();
			
			List<Object> sqlParams = new ArrayList<Object>();
			String sql = this.getFindPageNameCodeByDescQuerySql(sqlParams, pageNameDesc);
			
			ps = conn.prepareStatement(sql);
			DBean.setParameters(ps, sqlParams);
			rs = ps.executeQuery();
			
			String pageNameCode = null;
			while (rs.next()){
				pageNameCode = rs.getString("page_name_code");
			}
			return pageNameCode;
		} catch (Exception e) {
			throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
	}
	
	private String getFindPageNameCodeByDescQuerySql(List<Object> sqlParams, String pageNameDesc) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(" select page_name_code from t_uiconfig_page_name where 1=1 ");
		
		if (StringUtils.isNotEmpty(pageNameDesc)) {
			sb.append(" and page_name_desc = ?");
			sqlParams.add(pageNameDesc);
		}
		
		return sb.toString();
	}

	@Override
	public List<String> findAllPageNameDesc() throws Exception {
		// TODO Auto-generated method stub
		DBean db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			db = new DBean();
			db.connect();
			Connection conn = db.getConnection();
			
			List<Object> sqlParams = new ArrayList<Object>();
			String sql = "select * from t_uiconfig_page_name";
			
			ps = conn.prepareStatement(sql);
			DBean.setParameters(ps, sqlParams);
			rs = ps.executeQuery();
			
			List<String> pageNameDescList = new ArrayList<String>();
			while (rs.next()){
				pageNameDescList.add(rs.getString("page_name_desc"));
			}
			return pageNameDescList;
		} catch (Exception e) {
			throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
	}

}
