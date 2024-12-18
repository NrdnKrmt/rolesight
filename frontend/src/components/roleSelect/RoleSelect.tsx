import "./RoleSelect.css"

type Props = {
    setSelectedRole: (role: string) => void;
}

export default function RoleSelect({setSelectedRole}: Readonly<Props>) {

    return (
        <select id="role-select" onChange={event => setSelectedRole(event.target.value)}>
            <option value="">Select a role</option>
            <option value="Tank">Tank</option>
            <option value="Damage Dealer">Damage Dealer</option>
            <option value="Support">Support</option>
        </select>
    )
}