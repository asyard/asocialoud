<template>
    <div id="app">

        <div v-if="$store.getters.isLoggedIn == false">
            <img alt="App logo" src="./assets/logo.png" @click="home()">

            <p>
                Welcome to the new social platform!
            </p>
        </div>

        <div v-else>
            <img alt="App logo" src="./assets/asocialoud_mini.png" @click="home()">
            <h3>Welcome again!</h3>

            <input id="selector" name="selector" placeholder="search for @member" autocomplete="off"
                   v-model="membernameforsearch" class="form-control" v-on:keyup="getFilteredMembers"/>
            <div v-if="searchComplete">
                <b-list-group>
                    <b-list-group-item v-for="user in users" :key="user.id">
                        <a :href="$router.resolve('/profile/'+user.memberLoginName).href">{{user.memberLoginName}}</a>
                        <span v-if="$store.getters.getUserName!=user.memberLoginName && !user.followedByMe"><b-btn @click="followMember(user.memberLoginName)">follow</b-btn></span>
                        <span v-if="$store.getters.getUserName!=user.memberLoginName && user.followedByMe"><b-btn @click="unfollowMember(user.memberLoginName)">unfollow</b-btn></span>
                    </b-list-group-item>
                </b-list-group>
            </div>

            <ul>
                <li>
                    <!--router-link to="/feed">My feed</router-link-->
                    <a :href="$router.resolve('/feed').href">My Feed</a>
                </li>
                <li>
                    <a :href="$router.resolve('/profile/'+$store.getters.getUserName).href">My Profile</a>
                </li>
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
    import followapi from './follow-api';
    import store from "./store";


    export default {
        name: 'app',
        data() {
            return {
                membernameforsearch: '',
                searchComplete: false,
                users: {
                    memberLoginName: '',
                    followedByMe: false,
                    followsMe: false,
                    blockedByMe: false
                },
            }
        },
        methods: {
            home() {
                this.membernameforsearch = '';
                this.searchComplete = false;
                this.$router.push('/');
            },
            getFilteredMembers() {
                this.users = [];
                this.searchComplete = false;
                if (this.membernameforsearch.length >= 3 && this.membernameforsearch.startsWith('@')) {
                    userapi.getFiltered(store.getters.getUserName, this.membernameforsearch.substr(1, this.membernameforsearch.length)).then(response => {
                        this.users = response.data.data;
                    })
                    // eslint-disable-next-line
                        .catch(e => {
                            this.users = [];
                        })
                    this.searchComplete = true;
                }
            },
            followMember(memberToFollow) {
                followapi.addFollowing(store.getters.getUserName, memberToFollow);
            },
            unfollowMember(memberToUnFollow) {
                followapi.removeFollowing(store.getters.getUserName, memberToUnFollow);
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
