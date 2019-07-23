<template>
    <div class="user">

        <h2>Hello {{loggedInUserName}}, this is your member area</h2>




        <button @click="logout()">Logout</button>


        <button @click="listMembers()">List Members</button>


        <div v-if="hasData">
            here are all members :

            <ol>
                <li v-for="user in users" :key="user.id">{{user.loginName}} {{user.realName}} <button @click="deleteSelected(user.id)">delete</button>   </li>
            </ol>

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
            },
            logout() {
                this.$store.dispatch("logout", {})
                    .then(() => {
                        this.$router.push('/');
                    })
                    .catch(e => {
                        this.errors.push(e);
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