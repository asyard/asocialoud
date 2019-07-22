<template>
    <div id="app">
        <img alt="App logo" src="./assets/logo.png" @click="home()">

        <p>
            Welcome to the new social platform!
        </p>

        <div v-if="isUserLoggedIn">
            Hello {{loggedInUserName}}, you can access your feed from <router-link to="/feed">here</router-link>
            <router-view/>
        </div>

        <div v-else>
            Sign up from <router-link to="/register">here</router-link>. <br/>
            Already a member? <router-link to="/login">Sign in</router-link>
            <br/>
            <router-view/>

        </div>

        <br/>
        <h4>Follow us</h4>
        <ul>
            <li><a href="https://twitter.com" target="_blank" rel="noopener">twitter</a></li>
            <li><a href="https://instagram.com" target="_blank" rel="noopener">instagram</a></li>
        </ul>


    </div>
</template>

<script>
    export default {
        name: 'app',
        data() {
            return {
                isUserLoggedIn : this.$store.getters.isLoggedIn,
                loggedInUserName: this.$store.getters.getUserName,
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
    #app {
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
