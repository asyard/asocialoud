<template>
    <div id="app">

        <div  v-if="$store.getters.getUserName == null">
            <img alt="App logo" src="./assets/logo.png" @click="home()">

            <p>
                Welcome to the new social platform!
            </p>
        </div>

        <div v-else>
            <h3>Welcome again!</h3>

            <input type="text" id="selector" placeholder="search for @member" v-model="membernameforsearch" class="form-control" v-on:keyup="getFilteredMembers"/>
            <div v-if="searchComplete">
                <b-list-group>
                    <b-list-group-item v-for="user in users" :key="user.id">{{user.loginName}}<b-btn >follow</b-btn></b-list-group-item>
                </b-list-group>
            </div>
            <ul>
                <li><router-link to="/feed">My feed</router-link></li>
                <li><router-link :to="{ path: '/profile/'+$store.getters.getUserName}">My profile</router-link></li>
            </ul>
        </div>
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
    import userapi from './member-api';

    export default {
        name: 'app',
        data() {
            return {
                membernameforsearch: '',
                searchComplete: false,
                users: {
                    loginName: ''
                },
            }
        },
        methods: {
            home() {
                this.$router.push('/');
            },
            getFilteredMembers() {
                this.users = [];
                this.searchComplete = false;
                if (this.membernameforsearch.length >= 3 && this.membernameforsearch.startsWith('@')) {
                    userapi.getFiltered(this.membernameforsearch.substr(1, this.membernameforsearch.length)).then(response => {
                        this.users = response.data.data;
                    })
                    // eslint-disable-next-line
                        .catch(e => {
                            this.users = [];
                        })
                    this.searchComplete = true;
                }
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

    #selector {
        width: 250px;
        margin-left: auto;
        margin-right: auto;
    }


</style>
