<template>
    <div id="welcome">

        <div v-if="isUserLoggedIn">
            {{$t('welcomeMsg', {user: loggedInUserName})}} <router-link to="/feed">{{$t('here')}}</router-link>
            <!--i18n path="welcomeMsg" tag="p"> <a place="user"></a> </i18n-->
            <router-view/>
        </div>

        <div v-else>
            {{$t('signUpMsg')}} <router-link to="/register">{{$t('here')}}</router-link> <br/>
            {{$t('loginMsg')}} <router-link to="/login">{{$t('signIn')}}</router-link>
            <br/>
            <router-view/>

        </div>

    </div>
</template>

<script>
    export default {
        name: 'welcome',
        data() {
            return {
                isUserLoggedIn : this.$store.getters.isLoggedIn,
                loggedInUserName: this.$store.getters.getRealName,
            }
        },
        methods: {
            home() {
                this.$router.push('/');
            }
        }
    }
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
    #welcome {
        font-family: 'Trebuchet MS', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        margin-top: 60px;
    }

    h3 {
        margin: 40px 0 0;
    }

    ul {
        list-style-type: none;
        padding: 0;
    }

    li {
        display: inline-block;
        margin: 0 10px;
    }

    a {
        color: #42b983;
    }


</style>
