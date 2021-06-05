package com.khelacademy.daoImpl;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.khelacademy.dao.CityDao;
import com.khelacademy.www.pojos.ApiFormatter;
import com.khelacademy.www.pojos.Cities;
import com.khelacademy.www.pojos.Event;
import com.khelacademy.www.pojos.MyErrors;
import com.khelacademy.www.services.ServiceUtil;
import com.khelacademy.www.utils.RedisBullet;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class CityDaoImpl implements CityDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);
    JedisPool jedisPool = RedisBullet.getPool();
	Jedis jedis = jedisPool.getResource();
	//jedis.get(Integer.toString(rs.getInt("event_id")));
   
	@Override
	public Response getAllCities() {
		 List<Cities> cityMap = new ArrayList<Cities>();
		try {
		    Set<String> names=jedis.keys("ACTIVE_CITIES_*");
		    Iterator<String> it = names.iterator();
		    while (it.hasNext()) {
		    	
		        String s = it.next();
		        Cities city = new Cities();
		        city.setCityName(jedis.get(s));
		        city.setCityId(Integer.parseInt(s.substring(s.indexOf("ES_")+3, s.length())));
		        cityMap.add(city);
		    }
		}catch(Exception e) {
			LOGGER.error("Error in getting Cities "+ e.getMessage());
			MyErrors error = new MyErrors(e.getMessage());
        	ApiFormatter<MyErrors>  err= ServiceUtil.convertToFailureResponse(error, "true", 500);
            return Response.ok(new GenericEntity<ApiFormatter<MyErrors>>(err) {
            }).build();
		}finally {
			jedis.close();
			jedisPool.close();
		}

    	ApiFormatter<List<Cities>>  cts= ServiceUtil.convertToSuccessResponse(cityMap);
        return Response.ok(new GenericEntity<ApiFormatter<List<Cities>>>(cts) {
        }).build();
	}

}
