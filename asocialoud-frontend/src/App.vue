<template>
    <div id="app">
        <img alt="App logo" src="./assets/logo.png" @click="home()">

        <p>
            Welcome to the new social platform!
        </p>

        <router-view/>

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
