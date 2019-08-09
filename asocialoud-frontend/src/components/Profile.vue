<template>
    <div class="profile">

        <div v-if="$route.params.username == $store.getters.getUserName">

            <h2>Your profile</h2>

            <b-btn @click="logout()">Logout</b-btn>

            <b-btn @click="listFollowing()">Following</b-btn>

            <b-btn @click="listFollowers()">Your Followers</b-btn>

            <b-btn @click="retrieveUser()">Update Profile</b-btn>

            <div v-if="followingDivEnabled">
                <h3>Following</h3>
                <b-list-group>
                    <b-list-group-item v-for="fd in user.following" :key="fd.id"><a :href="$router.resolve('/profile/'+fd.memberToFollow.loginName).href">{{fd.memberToFollow.loginName}}</a><b-btn @click="unfollowSelected(fd.memberToFollow.loginName)">unfollow</b-btn></b-list-group-item>
                </b-list-group>
            </div>

            <div v-if="followersDivEnabled">
                <h3>Your Followers</h3>
                <b-list-group>
                    <b-list-group-item v-for="fd in user.following" :key="fd.id"><a :href="$router.resolve('/profile/'+fd.owner.loginName).href">{{fd.owner.loginName}}</a><b-btn @click="alert(1)">block</b-btn></b-list-group-item>
                </b-list-group>
            </div>



            <div v-if="updateDivEnabled">
                <h3>Update Profile</h3>
                <form @submit.prevent="updateUser()">
                    <input type="text" v-model="user.realName" placeholder="real name" autofocus> <br/>
                    <input type="text" placeholder="email" v-model="user.email"><br/>
                    <b-btn variant="success" type="submit">Update</b-btn>
                    <b-btn @click="updateDivEnabled = false">Cancel</b-btn>
                </form>
            </div>

            <div v-if="hasError">
                Opps, something went wrong. {{message}}
            </div>

            <div v-else>
                {{message}}
            </div>

        </div>

        <div v-else-if="user.exists">
            Profile of : {{$route.params.username}}<br/>
            Real Name : {{user.realName}}
        </div>

        <div v-else>
            Sorry, user not found.

        </div>


    </div>


</template>

<script>
    import userapi from '../member-api';
    import followapi from '../follow-api';
    import store from '../store';


    export default {
        name: "Profile",
        data() {
            return {
                updateDivEnabled: false,
                followingDivEnabled: false,
                followersDivEnabled: false,
                hasError: false,
                message: '',
                user: {
                    realName: '', //store.getters.getRealName,
                    userName: '', //store.getters.getUserName,
                    email: '',
                    exists: false,
                    following: []
                }
            }
        },
        created() {
            document.title = 'Profile of ' + this.$route.params.username;
            userapi.retrieveByUserName(this.$route.params.username).then(response => {
                this.user.exists = true;
                this.user.realName = response.data.data.realName;
            })
            // eslint-disable-next-line
                .catch(e => {
                    this.user.exists = false;
                })
        },
        methods: {
            retrieveUser() {
                this.hasError = false;
                this.updateDivEnabled = false;
                this.followingDivEnabled = false;
                this.followersDivEnabled = false;
                userapi.retrieveByUserName(store.getters.getUserName).then(response => {
                    this.updateDivEnabled = true;
                    this.user.realName = response.data.data.realName;
                    this.user.email = response.data.data.email;
                })
                // eslint-disable-next-line
                    .catch(e => {
                        this.updateDivEnabled = false;
                        this.hasError = true;
                    })
            },
            updateUser() {
                // eslint-disable-next-line
                userapi.updateByUserName(store.getters.getUserName, this.user.realName, this.user.email).then(response => {
                    this.hasError = false;
                    this.message = "success";
                    this.updateDivEnabled = false;
                    this.$store.dispatch("refreshValues", {realname: this.user.realName, email: this.user.email})
                        .then(() => {
                            this.$router.push('/profile/' + store.getters.getUserName);
                        })
                        .catch(e => {
                            this.errors.push(e);
                        })
                })
                    .catch(e => {
                        this.updateDivEnabled = true;
                        this.hasError = true;
                        this.message = e;
                    })
            },

            listFollowing() {
                this.hasError = false;
                this.updateDivEnabled = false;
                this.followingDivEnabled = true;
                this.followersDivEnabled = false;
                this.user.following = [];
                followapi.getFollowing(store.getters.getUserName).then(response => {
                    this.user.following = response.data.data;
                })
                // eslint-disable-next-line
                    .catch(e => {
                        this.hasError = true;
                    })
            },

            listFollowers() {
                this.hasError = false;
                this.updateDivEnabled = false;
                this.followingDivEnabled = false;
                this.followersDivEnabled = true;
                this.user.following = [];
                followapi.getFollowers(store.getters.getUserName).then(response => {
                    this.user.following = response.data.data;
                })
                // eslint-disable-next-line
                    .catch(e => {
                        this.hasError = true;
                    })
            },

            unfollowSelected(userToRemove) {
                followapi.removeFollowing(store.getters.getUserName, userToRemove).then(response => {
                    this.user.following = response.data.data;
                })
                // eslint-disable-next-line
                    .catch(e => {
                        this.hasError = true;
                    })
            },

            logout() {
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

<style scoped>

</style>