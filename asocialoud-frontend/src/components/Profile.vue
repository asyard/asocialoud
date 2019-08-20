<template>
    <div class="profile">

        <div v-if="$route.params.username == $store.getters.getUserName">

            <h2>Your profile</h2>

            Your Name : {{$store.getters.getRealName}} <br/>

            <b-btn @click="listFollowing()">Following</b-btn>

            <b-btn @click="listFollowers()">Your Followers</b-btn>

            <b-btn @click="retrieveUser()">Update Profile</b-btn>


            <b-btn @click="deleteAccount(5)">Delete Account</b-btn>

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
            Real Name : {{user.realName}} <br/>

            <span v-if="!user.iFollow"><b-btn @click="followSelected($route.params.username)">follow</b-btn></span>
            <span v-if="user.iFollow"><b-btn @click="unfollowSelected($route.params.username)">unfollow</b-btn></span>
            <br/>

            <b-btn @click="listUserFeeds()">List Feeds</b-btn>
            <div v-if="hasProfileFeedData">
                <h4>{{$route.params.username}} feeds</h4>
                <b-list-group>
                    <b-list-group-item v-for="feed in feeds" :key="feed.id">{{feed.text}} <br/> {{feed.publishDate | moment("DD.MM.YYYY hh:mm:ss")}}</b-list-group-item>
                </b-list-group>
                <b-btn @click="listUserFeeds">load older</b-btn>
            </div>

            <div v-else-if="hasError">Opps. Something bad happened</div>

        </div>

        <div v-else>
            Sorry, user not found.

        </div>


    </div>


</template>

<script>
    import userapi from '../member-api';
    import followapi from '../follow-api';
    import feedapi from '../feed-api';
    import store from '../store';


    export default {
        name: "Profile",
        data() {
            return {
                updateDivEnabled: false,
                followingDivEnabled: false,
                followersDivEnabled: false,
                hasError: false,
                hasProfileFeedData: false,
                profileFeedDataCursor: 0,
                message: '',
                user: {
                    realName: '',
                    userName: '',
                    email: '',
                    id: '',
                    exists: false,
                    iFollow: false,
                    iBlock: false,
                    following: []
                },
                feeds:{
                    text:'',
                    publishDate:'',
                }
            }
        },
        created() {
            this.user.exists = false;
            userapi.retrieveByUserName(this.$route.params.username).then(response => {
                if (response.data.status == 200) {
                    document.title = 'Profile of ' + this.$route.params.username;
                    this.user.exists = true;
                    this.user.realName = response.data.data.memberRealName;
                    this.user.iFollow = response.data.data.followedByMe;
                    this.user.id = response.data.data.id;
                }
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
                    this.user.realName = response.data.data.memberRealName;
                    this.user.email = response.data.data.memberEmail;

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

            followSelected(userToAdd) {
                followapi.addFollowing(store.getters.getUserName, userToAdd).then(response => {
                    this.user.following = response.data.data;
                    this.user.iFollow=true;
                })
                // eslint-disable-next-line
                    .catch(e => {
                        this.hasError = true;
                    })
            },

            unfollowSelected(userToRemove) {
                followapi.removeFollowing(store.getters.getUserName, userToRemove).then(response => {
                    this.user.following = response.data.data;
                    this.user.iFollow=false;
                })
                // eslint-disable-next-line
                    .catch(e => {
                        this.hasError = true;
                    })
            },

            listUserFeeds() {
                this.hasProfileFeedData = false;
                this.hasError = false;
                feedapi.getFeedsOf(this.user.id, this.profileFeedDataCursor).then(response => {
                    if (response.data.status == 200) {
                        if (this.profileFeedDataCursor == 0) {
                            this.feeds = response.data.data;
                        } else {
                            this.feeds.push.apply(this.feeds,response.data.data)
                        }
                        this.hasProfileFeedData = true;
                        this.profileFeedDataCursor++;
                    }

                })
                    .catch(e => {
                        this.hasError = true;
                    })
            },

            deleteAccount() {
                this.$bvModal.msgBoxConfirm('Are you sure? All your data will be lost', {
                    title: 'Please Confirm',
                    size: 'sm',
                    buttonSize: 'sm',
                    okVariant: 'danger',
                    okTitle: 'YES',
                    cancelTitle: 'NO',
                    footerClass: 'p-2',
                    hideHeaderClose: false,
                    centered: true
                })
                    .then(value => {
                        if (value) {
                            userapi.removeForUserName(store.getters.getUserName).then(response => {
                                if (response.data.status == 200) {
                                    this.logout();
                                } else {
                                    throw "Delete failed";
                                }
                            })
                                .catch(e => {
                                    this.hasError = true;
                                    //this.message = 'Unable to delete account. Please try later. Sorry. Really.';
                                })
                        }
                    })
                    .catch(err => {
                        this.hasError = true;
                        //this.message = 'Unable to delete account. Please try later. Sorry. Really.';
                    })
            }
        }
    }
</script>

<style scoped>

</style>