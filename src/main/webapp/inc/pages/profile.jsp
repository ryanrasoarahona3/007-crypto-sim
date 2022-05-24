<%@ page import="com.crypto.cryptosim.DatabaseManager" %>
<%@ page import="com.crypto.cryptosim.models.User" %>
<%@ page import="com.crypto.cryptosim.services.SessionManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
   #ui-datepicker-div{
      background: white;
      border: 1px solid #ccc;
      padding: 1em;
   }
</style>
<%
   DatabaseManager.getInstance().init(request.getServletContext());
   User u = SessionManager.getInstance().getActiveUser(request);
%>
<form method="post" action="profile">

   <div class="my-3">
      <h2>My profile</h2>
   </div>

   <div class="row my-3">
      <div class="col-md-6">
         <div class="text-center">
            <img src="images/default-profile.jpg" width="180px"/>
         </div>
         <div class="my-2">
            <label for="picture" class="form-label">Change your profile picture</label>
            <input class="form-control" type="file" id="picture" name="picture">
         </div>
         <div class="my-4">
            <label for="birth">Your date of birth</label>
            <input class="form-control" type="text" id="birth" name="birth" value="<%= u.getBirth() %>"/>
         </div>
         <div class="my-4">
            <label for="gender">Gender</label>
            <select class="form-select" id="gender" name="gender" value="<%= u.getGender() %>">
               <option value="UNKNOWN">Unspecified</option>
               <option value="MALE">Male</option>
               <option value="FEMALE">Female</option>
            </select>
         </div>
      </div>
      <div class="col-md-6">
         <div class="my-4">
            <label for="firstname">Firstname</label>
            <input class="form-control" type="text" id="firstname" name="firstname" value="<%= u.getFirstname() %>"/>
         </div>
         <div class="my-4">
            <label for="lastname">Lastname</label>
            <input class="form-control" type="text" id="lastname" name="lastname" value="<%= u.getLastname() %>"/>
         </div>
         <div class="my-4">
            <label for="email">Email address</label>
            <input class="form-control" type="email" id="email" name="email" value="<%= u.getEmail() %>"/>
         </div>
         <div class="my-4">
            <label for="phone">Telephone</label>
            <input class="form-control" type="text" id="phone" name="phone" value="<%= u.getPhone() %>"/>
         </div>
         <div class="my-4">
            <!-- TODO: problème d'accentuation à résoudre -->
            <!-- TODO: photo de profil -->
            <!-- TODO: bug sur la date -->
            <label for="address">Addresse</label>
            <input class="form-control" type="text" id="address" name="address" value="<%= u.getAddress() %>">
         </div>
      </div>
   </div>

   <div class="my-2 text-end">
      <input type="submit" class="btn btn-primary" value="Modify"/>
   </div>
</form>


<script>
   $(()=>{
      $("#birth").datepicker()
   })
</script>