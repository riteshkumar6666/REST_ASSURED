package com.bridgelabz.spotify;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class spotifyApi {
	String userID="31xkkgo6bxlu4e7zpjnylih722ki";
	String playlistId;
	String track;
	String token;
	@BeforeTest
	public void getToken()
	{
		token="Bearer BQCXLR3xDtgaqIwEr1op4oyOFB9_rZC5gn8RoUKZQZmz2QzmPwzUG-JxFKDidxPbn0b1r4OjT6W08fD94gONfgSgFpo_M0Gh8-naDNRnCHOUkKU-zdzK71SOtB592YvxxlQHiqtXtIQ4wYdRViVG7IqGdAuICjPdaNx5dL-J8hQwPuCK9flHY16t1GGqQltdcSPFrMWVnoairIBPK_am6RhTh-DkLyLoUmaWkkB_AcA5JtA1EXsBz6OdsMF5LRXsF3tqWYM5XhWT6fKuZm0ZFDoWt7NgkUrWGvcU2z_UTEs5gBzXqp5r3Hury8YUnZ--F18";
	}
	@BeforeTest
	public void getPlaylistId()
	{
		playlistId="26P56HFdDhEjGjBf3HKhle";
	}
	@BeforeTest
	public void getTrack()
	{
		track="spotify:track:2jt9VV6QGn0tbwkrr26OO6";
	}
	@Test(priority =0)
	public void testGet_CurrentUsersProfile() {
		Response response = given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.get(" http://api.spotify.com/v1/me");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority =1)
	public void testGet_Users_Profile() {
		Response response = given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.get("	https://api.spotify.com/v1/users/"+userID +"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);

	}
	@Test(priority =2)
	public void createPlaylist() {
		Response response =RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.body("{\r\n"
									+ "  \"name\": \"Ritesh Playlist update\",\r\n"
									+ "  \"description\": \"New playlist description\",\r\n"
									+ "  \"public\": false\r\n"
									+ "}")		
							.when()		
							.post("https://api.spotify.com/v1/users/"+userID+"/playlists\r\n"
									+ "");
		response.prettyPrint();
		response.then().assertThat().statusCode(201);

	}
	@Test(priority =3)
	public void GetUserPlaylist() {
		Response response =RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
						
							.when()
							.get("https://api.spotify.com/v1/users/"+userID+"/playlists\r\n"
									+ "");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);

	}


@Test(priority =4)
public void AdditemToPlaylist() {
	Response response =RestAssured.given().contentType(ContentType.JSON)
						.accept(ContentType.JSON)
						.header("Authorization", token)
						.queryParams("uris",track)				
						.when()
						.post("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks\r\n"
								+ "");
	response.prettyPrint();
	response.then().assertThat().statusCode(201);

}


@Test(priority =5)
public void test_Get_currentuserprofile_shouldReturn_statuscode200() {
	Response response =RestAssured.given().contentType(ContentType.JSON)
						.accept(ContentType.JSON)
						.header("Authorization", token)
					
						.when()
						.get("https://api.spotify.com/v1/me\r\n"
								+ "");
	response.prettyPrint();
	userID=response.path("id");
	System.out.println("User id is :" +userID);
	response.then().assertThat().statusCode(200);

	int statusCode = response.getStatusCode();
	System.out.println("Status code:" +statusCode);
	Assert.assertEquals(userID, "31xkkgo6bxlu4e7zpjnylih722ki");
	
}
@Test(priority =6)
public void SearchForitem() {
	Response response =RestAssured.given().contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Authorization", token)
			.queryParam("q","Arijit singh")
			.queryParam("type","track")
			.when()
			.get("https://api.spotify.com/v1/search\r\n"
					+ "");
	response.prettyPrint();
	response.then().assertThat().statusCode(200);

}
@Test(priority =7)
public void removed_Playlist_Items() {
	Response response =RestAssured.given().contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Authorization", token)
			.body("{\"tracks\":[{\"uri\":\"spotify:track:4RbRZYPR7Dwg8WRNn4UA8m\"}]}")
	        .when()
			.delete("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
	response.prettyPrint();
	response.then().assertThat().statusCode(200);

}
@Test(priority =8)

public void updatePlaylist() {
	Response response =RestAssured.given().contentType(ContentType.JSON)
						.accept(ContentType.JSON)
						.header("Authorization", token)
						.body("{\r\n"
								+ "  \"range_start\": 1,\r\n"
								+ "  \"insert_before\": 3,\r\n"
								+ "  \"range_length\": 2\r\n"
								+ "} ")
						.when()
						.put("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks\r\n"
								+ "");
	response.prettyPrint();
	response.then().assertThat().statusCode(200);

	
}

@Test(priority =9)
public void change_Playlist_Details() {
	Response response =RestAssured.given().contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Authorization", token)
			.body("{\r\n"
					+ "  \"name\": \"BITCOIN\",\r\n"
					+ "  \"description\": \"Updated playlist description\",\r\n"
					+ "  \"public\": false\r\n"
					+ "}")
			.when()
			.put("https://api.spotify.com/v1/playlists/"+playlistId+"");
	response.prettyPrint();
	response.then().assertThat().statusCode(200);

}
@Test(priority =10)
public void get_Playlist() {
	Response response =RestAssured.given().contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Authorization", token)
			.when()
			.get("	https://api.spotify.com/v1/playlists/"+playlistId+"");
	response.prettyPrint();
	response.then().assertThat().statusCode(200);

}
@Test(priority =11)
public void Track_Audio_Analysis() {
	Response response =RestAssured.given().contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Authorization", token)
			.when()
			.get("https://api.spotify.com/v1/audio-analysis/2jt9VV6QGn0tbwkrr26OO6\r\n"
					+ "");
	response.prettyPrint();
	response.then().assertThat().statusCode(200);


}
@Test(priority =12)
public void GetTrack_Audio_Features() {
	Response response =RestAssured.given().contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Authorization", token)
			.when()
			.get("https://api.spotify.com/v1/audio-features\r\n"
					+ "");
	response.prettyPrint();
	response.then().assertThat().statusCode(200);


}
@Test(priority =13)
public void Get_Track_Audio_Features() {
	Response response =RestAssured.given().contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Authorization", token)
			.when()
			.get("https://api.spotify.com/v1/audio-features/5W7DOVGQLTigu09afW7QMT\r\n"
					+ "");
	response.prettyPrint();
	response.then().assertThat().statusCode(200);



}
@Test(priority =14)
public void Get_Several_Tracks() {
	Response response =RestAssured.given().contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Authorization", token)
			.when()
			.get("https://api.spotify.com/v1/tracks\r\n"
					+ "");
	response.prettyPrint();
	response.then().assertThat().statusCode(200);


}
@Test(priority =15)
public void Get_Tracks() {
	Response response =RestAssured.given().contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Authorization", token)
			.when()
			.get("https://api.spotify.com/v1/tracks/14uKipECtBQCcwr8VnW5ey\r\n"
					+ "");
	response.prettyPrint();
	response.then().assertThat().statusCode(200);


}
@Test(priority =16)
public void getPlaylistCoverImage() {
	Response response = RestAssured.given().contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Authorization", token)
			.when()
			.get("https://api.spotify.com/v1/playlists/"+playlistId+"/images");
	response.prettyPrint();
	response.then().assertThat().statusCode(200);
}
@Test(priority =17)
public void get_Playlist_Items() {
	Response response =RestAssured.given().contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Authorization", token)
			.when()
			.get("	https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
	response.prettyPrint();
	response.then().assertThat().statusCode(200);

}
}










	
	
	


