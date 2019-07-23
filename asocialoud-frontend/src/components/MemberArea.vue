<template>
    <div class="user">

        <h2>Hello {{loggedInUserName}}, this is your feed</h2>

        <b-btn @click="listMembers()">List Members</b-btn>


        <div v-if="hasData">
            <h5>Here are all members : </h5>

            <b-list-group>
                <b-list-group-item v-for="user in users" :key="user.id">{{user.loginName}} {{user.realName}} <b-btn @click="deleteSelected(user.id)">delete</b-btn></b-list-group-item>
            </b-list-group>
        </div>




    </div>
</template>

<script>
    import userapi from '../member-api';
    import store from '../store';

    export default {
        name: "MemberArea",
        data() {
            return {
                hasData : false,
                loggedInUserName: store.getters.getRealName,
                memberList: '',
                errors: [],
                users: {
                    loginName: '',
                    realName: '',
                    id: -1
                },
            }
        },
        methods: {
            listMembers() {
                userapi.getAll().then(response => {
                    // JSON responses are automatically parsed.
                    this.users = response.data.data;
                    this.hasData = true;
                })
                    .catch(e => {
                        this.hasData = false;
                        this.loginError = true;
                        this.errors.push(e);
                        this.users = e;
                    })
            },

            deleteSelected(id) {
                // eslint-disable-next-line
                userapi.removeForId(id).then(response => {
                    // JSON responses are automatically parsed.
                    this.listMembers();
                })
                    .catch(e => {
                        this.hasData = false;
                        this.loginError = true;
                        this.errors.push(e);
                        this.users = e;
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