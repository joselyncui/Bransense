package com.matrix.brainsense.restful;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.matrix.brainsense.action.UserAction;
import com.matrix.brainsense.entity.BasePackage;
import com.matrix.brainsense.entity.Category;
import com.matrix.brainsense.entity.ContentPackage;
import com.matrix.brainsense.entity.Country;
import com.matrix.brainsense.entity.Language;
import com.matrix.brainsense.entity.PackagePath;
import com.matrix.brainsense.entity.Type;
import com.matrix.brainsense.entity.User;
import com.matrix.brainsense.entity.Watchdog;
import com.matrix.brainsense.entity.XBMCInfo;

@Path("/user")
@Controller
public class UserRestful {
	
	@Autowired
	private UserAction userAction;
	
	@Path("/user/logout/{macAdd}")
	@PUT
	@Produces(value = MediaType.TEXT_PLAIN)
	public String logout(@PathParam("macAdd") String macAdd){
		return String.valueOf(userAction.logout(macAdd));
	}
	
	@Path("/device/status/{macAdd}")
	@GET
	@Produces(value = MediaType.TEXT_PLAIN)
	public String getDeviceStatus(@PathParam("macAdd") String macAdd){
		return String.valueOf(userAction.getDeviceStatus(macAdd));
	}
	
	@Path("/user/status/{macAdd}/{deviceId}")
	@GET
	@Produces(value = MediaType.TEXT_PLAIN)
	public String getUserStatus(@PathParam("macAdd") String macAdd, @PathParam("deviceId") String deviceId){
		return String.valueOf(userAction.getUserStatus(macAdd, deviceId));
	}
	
	@Path("/user")
	@POST
	@Produces(value = MediaType.TEXT_PLAIN)
	public String userRegister(@QueryParam("macAdd") String macAdd, @QueryParam("name") String name, @QueryParam("email") String email,
			@QueryParam("keyCode") String keyCode, @QueryParam("deviceId") String deviceId){
		return String.valueOf(userAction.userRegister(macAdd, name, email, keyCode, deviceId));
	}
	
	@Path("/userhardware")
	@POST
	@Produces(value = MediaType.TEXT_PLAIN)
	public String saveUserHardware(@QueryParam("macAdd") String macAdd, @QueryParam("cpu") String cpu,
			@QueryParam("os") String os, @QueryParam("memory") String memory, @QueryParam("sdcard") String sdcard){
		return String.valueOf(userAction.saveUserHardware(macAdd, cpu, os, memory, sdcard));
	}
	
	@Path("/languages")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public List<Language> getAvailableLanguage(){
		return userAction.getDownloadableLanguage();
	}
	
	@Path("/types")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public List<Type> getAvailableType(){
		return userAction.getDownloadableType();
	}
	
	@Path("/base/{languageId}/{typeId}")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public BasePackage getBaseByLanguageAndType(@PathParam("languageId") String languageId,
			@PathParam("typeId") String typeId){
		BasePackage base;
		int languageIdNum = Integer.valueOf(languageId);
		int typeIdNum = Integer.valueOf(typeId);
		base = userAction.getBaseByTypeAndLanguage(languageIdNum, typeIdNum);
		if(base == null){
			return new BasePackage();
		}
		return base;
	}
	
	@Path("/countries")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public List<Country> getAllCountry(){
		return userAction.getDownloadableCountry();
	}
	
	@Path("/categories")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public List<Category> getAllCategory(){
		return userAction.getDownloadableCategory();
	}
	
	@Path("/contentpackages/{countryId}/{categoryId}")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public List<ContentPackage> getContentPackageByCountryAndCategory(
			@PathParam("countryId") String countryId, @PathParam("categoryId") String categoryId){
		int countryIdNum = Integer.valueOf(countryId);
		int categoryIdNum = Integer.valueOf(categoryId);
		return userAction.getPackageByCountryAndCategory(countryIdNum, categoryIdNum);
	}
	
	@Path("/localpackagepath/{macAdd}")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public User getLocalPackagePath(@PathParam("macAdd") String macAdd){
		return userAction.getLocalPackagePath(macAdd);
	}
	
	@Path("/localpackagepath")
	@PUT
	@Produces(value = MediaType.TEXT_PLAIN)
	public String updateLocalPackagePath(@QueryParam("macAdd") String macAdd, @QueryParam("type") String type){
		return String.valueOf(userAction.updateLocalPackagePath(macAdd, type));
	}
	
	@Path("/packagepath")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public PackagePath getPackagePath(){
		return userAction.getPackagePath();
	}
	
	@Path("/user/status")
	@PUT
	@Produces(value = MediaType.TEXT_PLAIN)
	public String updateUserDownloadStatus(@QueryParam("macAdd") String macAdd, @QueryParam("type") String type){
		return String.valueOf(userAction.updateUserDownloadStatus(macAdd, type));
	}
	
	@Path("/xbmc")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public XBMCInfo getLatestXBMC(){
		return userAction.getAvailableXbmc();
	}
	
	@Path("/watchdog")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public Watchdog getLatestWatchdog(){
		return userAction.getAvailableWatchdog();
	}
	
}
