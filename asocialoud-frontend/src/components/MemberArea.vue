<template>
    <div class="user">

        <h2>Hello {{loggedInUserRealName}}, this is your feed</h2>

        Add New Feed <br/>
        <form @submit.prevent="createNewFeed()">
            <textarea type="text" v-model="feedToPost.text" placeholder="say something"> </textarea> <br/>

            <b-btn variant="success" type="submit">Share</b-btn>
        </form>


        <b-btn @click="listFeeds()">List Your Feeds</b-btn>
        <b-btn @click="listFollowingFeeds()">List Your Followings' Feeds</b-btn>
        <b-btn @click="listMembers()">List Members</b-btn>

        <div v-if="hasFeedData">
            <h4>My feeds</h4>
            <b-list-group>
                <b-list-group-item v-for="feed in feeds" :key="feed.id">{{feed.text}} <br/> {{feed.publishDate |
                    moment("DD.MM.YYYY hh:mm:ss")}}
                </b-list-group-item>
            </b-list-group>
        </div>

        <div v-if="hasFollowFeedData">
            <h4>Your followings' feeds</h4>
            <b-list-group>
                <b-list-group-item v-for="feed in ffeeds" :key="feed.id">{{feed.text}} <br/> {{feed.publishDate |
                    moment("DD.MM.YYYY hh:mm:ss")}} <br/> from: {{feed.memberLoginName}}
                </b-list-group-item>
            </b-list-group>
        </div>

        <div v-if="hasError">
            Opps, something went wrong.
        </div>

        <div v-if="hasMemberData">
            <h5>Here are all members : </h5>

            <b-list-group>
                <b-list-group-item v-for="user in users" :key="user.id">{{user.loginName}} {{user.realName}}
                    <b-btn @click="deleteSelected(user.loginName)">delete</b-btn>
                </b-list-group-item>
            </b-list-group>
        </div>

    </div>
</template>

<script>
    import userapi from '../member-api';
    import feedapi from '../feed-api';
    import followapi from '../follow-api';
    import store from '../store';

    export default {
        name: "MemberArea",
        data() {
            return {
                hasMemberData: false,
                hasFeedData: false,
                hasFollowFeedData: false,
                hasError: false,
                loggedInUserRealName: store.getters.getRealName,
                feeds: {
                    text: '',
                    publishDate: '',
                },
                followingsFollowData: {
                    memberId: '',
                    loginName: ''
                },
                ffeeds: {
                    text: '',
                    publishDate: '',
                    memberId: '',
                    memberLoginName: ''
                },
                feedToPost: {
                    text: ''
                },
                errors: [],
                users: {
                    loginName: '',
                    realName: '',
                    id: -1
                },
            }
        },
        methods: {
            getMemberNameOf(memId) {
                for (let f = 0; f < this.followingsFollowData.length; f++) {
                    if (this.followingsFollowData[f].memberToFollow.id == memId) {
                        return this.followingsFollowData[f].memberToFollow.loginName;
                    }
                }

                return 'n/a';
            },
            createNewFeed() {
                this.hasError = false;
                feedapi.addFeed(store.getters.getUniqueId, this.feedToPost.text).then(response => {
                    if (response.data.status = 200) {
                        this.feedToPost.text = '';
                        this.listFeeds();
                    }
                })
                    .catch(e => {
                        this.hasError = true;
                    })
            },
            listFeeds() {
                this.hasMemberData = false;
                this.hasFollowFeedData = false;
                this.hasError = false;
                feedapi.getFeedsOf(store.getters.getUniqueId).then(response => {
                    if (response.data.status == 200) {
                        this.feeds = response.data.data;
                        this.hasFeedData = true;
                    }

                })
                    .catch(e => {
                        this.hasError = true;
                    })
            },

            listFollowingFeeds() {
                this.hasMemberData = false;
                this.hasError = false;
                this.hasFeedData = false;
                followapi.getFollowing(store.getters.getUserName).then(response => {
                    if (response.data.status == 200) {
                        this.followingsFollowData = response.data.data;

                        let followIds = this.followingsFollowData.map(a => a.memberToFollow.id);
                        if (followIds.length > 0) {
                            feedapi.getFeedsOfFollowing(followIds).then(response => {
                                if (response.data.status == 200) {
                                    this.ffeeds = response.data.data;
                                    for (let f = 0; f < this.ffeeds.length; f++) {
                                        this.ffeeds[f].memberLoginName = this.getMemberNameOf(this.ffeeds[f].memberId);
                                    }
                                    this.hasFollowFeedData = true;
                                }

                            })
                                .catch(e => {
                                    this.hasError = true;
                                });
                        }
                    }

                })
                    .catch(e => {
                        this.hasError = true;
                    });


            },

            listMembers() {
                this.hasFeedData = false;
                this.hasFollowFeedData = false;
                userapi.getAll().then(response => {
                    // JSON responses are automatically parsed.
                    this.users = response.data.data;
                    this.hasMemberData = true;
                })
                    .catch(e => {
                        this.hasMemberData = false;
                        this.errors.push(e);
                        this.users = [];
                    })
            },

            deleteSelected(userName) {
                // eslint-disable-next-line
                userapi.removeForUserName(userName).then(response => {
                    // JSON responses are automatically parsed.
                    this.listMembers();
                })
                    .catch(e => {
                        this.errors.push(e);
                        this.users = [];
                    })
            }
        }
    }
</script>

<style scoped>
    li {
        display: list-item;

    }
</style>