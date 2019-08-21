<template>
    <div class="user">

        <h2>Hello {{loggedInUserRealName}}, this is your feed</h2>

        Add New Feed <br/>
        <form @submit.prevent="createNewFeed()">
            <textarea type="text" v-model="feedToPost.text" placeholder="say something"> </textarea> <br/>

            <b-btn variant="success" type="submit">Share</b-btn>
        </form>


        <b-btn @click="listOwnFeeds(true)">List Your Feeds</b-btn>
        <b-btn @click="listFollowingFeeds(true)">List Your Followings' Feeds</b-btn>
        <b-btn @click="listMembers()">List Members</b-btn>

        <div v-if="hasFeedData" class="scrollable">
            <h4>My feeds</h4>
            <b-list-group>
                <b-list-group-item v-for="feed in feeds" :key="feed.id">{{feed.text}} <br/> {{feed.publishDate |
                    moment("DD.MM.YYYY hh:mm:ss")}}
                </b-list-group-item>
            </b-list-group>
            <b-btn @click="listOwnFeeds(false)" v-if="stillHasContent">load older</b-btn>
        </div>

        <div v-if="hasFollowFeedData" class="scrollable">
            <h4>Your followings' feeds</h4>
            <b-list-group>
                <b-list-group-item v-for="feed in ffeeds" :key="feed.id">{{feed.text}} <br/> {{feed.publishDate |
                    moment("DD.MM.YYYY hh:mm:ss")}} <br/> from: {{feed.memberLoginName}}
                </b-list-group-item>
            </b-list-group>
            <b-btn @click="listFollowingFeeds(false)" v-if="stillHasFContent">load older</b-btn>
        </div>

        <div v-if="hasError">
            Opps, something went wrong.
        </div>

        <div v-if="hasMemberData" class="scrollable">
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
                ownFeedDataCursor: 0,
                hasFollowFeedData: false,
                followingFeedDataCursor: 0,
                hasError: false,
                stillHasContent:true,
                stillHasFContent:true,
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
                        this.listOwnFeeds();
                    }
                })
                    .catch(e => {
                        this.hasError = true;
                    })
            },
            listOwnFeeds(clear) {
                this.hasMemberData = false;
                this.hasFollowFeedData = false;
                this.hasError = false;
                if (clear) {
                    this.feeds = [];
                    this.ownFeedDataCursor = 0;
                    this.stillHasContent = true;
                }
                feedapi.getFeedsOf(store.getters.getUniqueId, this.ownFeedDataCursor).then(response => {
                    if (response.data.status == 200) {
                        if (this.ownFeedDataCursor == 0) {
                            this.feeds = response.data.data;
                        } else {
                            this.feeds.push.apply(this.feeds, response.data.data);
                            if (response.data.data.length < 1) {
                                this.stillHasContent = false;
                            }
                        }
                        this.hasFeedData = true;
                        this.ownFeedDataCursor++;
                    }

                })
                    .catch(e => {
                        this.hasError = true;
                    });
            },

            listFollowingFeeds(clear) {
                this.hasMemberData = false;
                this.hasError = false;
                this.hasFeedData = false;
                if (clear) {
                    this.ffeeds = [];
                    this.followingFeedDataCursor = 0;
                    this.stillHasFContent = true;
                }
                followapi.getFollowing(store.getters.getUserName).then(response => {
                    if (response.data.status == 200) {
                        this.followingsFollowData = response.data.data;

                        let followIds = this.followingsFollowData.map(a => a.memberToFollow.id);
                        if (followIds.length > 0) {
                            feedapi.getFeedsOfFollowing(followIds, this.followingFeedDataCursor).then(response => {
                                if (response.data.status == 200) {
                                    if (this.followingFeedDataCursor == 0) {
                                        this.ffeeds = response.data.data;
                                    } else {
                                        this.ffeeds.push.apply(this.ffeeds, response.data.data);
                                        if (response.data.data.length < 1) {
                                            this.stillHasFContent = false;
                                        }
                                    }

                                    for (let f = 0; f < this.ffeeds.length; f++) {
                                        this.ffeeds[f].memberLoginName = this.getMemberNameOf(this.ffeeds[f].memberId);
                                    }
                                    this.hasFollowFeedData = true;
                                    this.followingFeedDataCursor++;
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
    .scrollable {
        height: 250px;
        overflow: auto;
    }
</style>