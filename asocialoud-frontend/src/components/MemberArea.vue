<template>
    <div class="user">

        <h2>Hello, this is your member area</h2>


        <button @click="listMembers()">List Members</button>


        <div v-if="hasData">
            here are all members :

            <ol>
                <li v-for="user in users" :key="user.id">{{user.loginName}} {{user.realName}} <p/> </li>
            </ol>

        </div>




    </div>
</template>

<script>
    import userapi from '../member-api';

    export default {
        name: "MemberArea",
        data() {
            return {
                hasData : false,
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
            }
        }
    }
</script>

<style scoped>
li {
    display: list-item;

}
</style>