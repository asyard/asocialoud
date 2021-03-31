<template>
    <div id="app">

        <div>
            <button v-for="entry in languages" :key="entry.title" @click="changeLocale(entry.language)">
                <!--flag :iso="entry.flag" v-bind:squared=false /--> {{entry.title}}
            </button>
        </div>

        <div v-if="$store.getters.isLoggedIn == false">
            <img alt="App logo" src="./assets/asocialoud_logo.png" @click="home()"><br/>
            <h4>asocialoud</h4>

            <p>
                {{$t('appTitle')}}
            </p>
        </div>

        <div v-else>
            <img alt="App logo" src="./assets/asocialoud_mini.png" @click="home()">
            <h4>asocialoud</h4>

            <input id="selector" name="selector" placeholder="search for @member" autocomplete="off"
                   v-model="membernameforsearch" class="form-control" v-on:keyup="getFilteredMembers"/>
            <div v-if="searchComplete">
                <b-list-group>
                    <b-list-group-item v-for="user in users" :key="user.id">
                        <a :href="$router.resolve('/profile/'+user.memberLoginName).href">{{user.memberLoginName}}</a>
                        <!--
                        <span v-if="$store.getters.getUserName!=user.memberLoginName && !user.followedByMe"><b-btn @click="followMember(user.memberLoginName)">follow</b-btn></span>
                        <span v-if="$store.getters.getUserName!=user.memberLoginName && user.followedByMe"><b-btn @click="unfollowMember(user.memberLoginName)">unfollow</b-btn></span>
                        -->
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
                <li>
                    <a @click="logout" href="#">Logout</a>
                </li>
            </ul>
        </div>
        <router-view/>

        <br/>

        <div id="footer">
            <h4>{{$t('followUs')}}</h4>
            <ul style="vertical-align: bottom">
                <li><a href="https://twitter.com" target="_blank" rel="noopener">twitter</a></li>
                <li><a href="https://instagram.com" target="_blank" rel="noopener">instagram</a></li>
            </ul>
        </div>


    </div>
</template>

<script>
    import userapi from './member-api';
    import followapi from './follow-api';
    import store from "./store";
    import i18n from "./i18n";


    export default {
        name: 'app',
        data() {
            return {
                languages: [
                    { flag: 'uk', language: 'en', title: 'English' },
                    { flag: 'tr', language: 'tr', title: 'Türkçe' }
                ],
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
            changeLocale(locale) {
                i18n.locale = locale;
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
            },
            followMember(memberToFollow) {
                followapi.addFollowing(store.getters.getUserName, memberToFollow).then(response => {
                    this.user.followedByMe=true;
                })
                // eslint-disable-next-line
                    .catch(e => {
                        this.hasError = true;
                    })
            },
            unfollowMember(memberToUnFollow) {
                followapi.removeFollowing(store.getters.getUserName, memberToUnFollow).then(response => {
                    this.user.followedByMe=false;
                })
                // eslint-disable-next-line
                    .catch(e => {
                        this.hasError = true;
                    })
            }, logout() {
                this.$store.dispatch("logout", {})
                    .then(() => {
                        window.location.href = "/";
                        //this.$router.push('/');
                    })
                    .catch(e => {
                        this.errors.push(e);
                    })
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

    #footer {
        //height: 40px;
        position: fixed;
        bottom:0%;
        width:100%;
        //background-color: #393838;
        opacity: 1;
    }


</style>
