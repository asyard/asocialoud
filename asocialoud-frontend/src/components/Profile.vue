<template>
    <div class="profile">

        <h2>Your profile</h2>

        <b-btn @click="retrieveUser()">Update Profile</b-btn>

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
</template>

<script>
    import userapi from '../member-api';
    import store from '../store';


    export default {
        name: "Profile",
        data() {
            return {
                updateDivEnabled: false,
                hasError : false,
                message : '',
                user: {
                    realName: '', //store.getters.getRealName,
                    userName: '', //store.getters.getUserName,
                    email: ''
                }
            }
        },
        methods: {
            retrieveUser() {
                this.hasError = false;
                this.updateDivEnabled = false;
                userapi.retrieveByUserName(store.getters.getUserName).then(response => {
                    this.updateDivEnabled = true;
                    this.user.realName = response.data.data.realName;
                    this.user.email = response.data.data.email;
                })
                    .catch(e => {
                        this.updateDivEnabled = false;
                        this.hasError = true;
                    })
            },
            updateUser() {
                userapi.updateByUserName(store.getters.getUserName, this.user.realName, this.user.email).then(response => {
                    this.hasError = false;
                    this.message = "success";
                    this.updateDivEnabled = false;
                    this.$store.dispatch("refreshValues", {realname: this.user.realName, email: this.user.email})
                        .then(() => {
                            this.$router.push('/profile');
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
            }
        }
    }
</script>

<style scoped>

</style>